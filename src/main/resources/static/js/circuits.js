/*  Circuits page
    - Renders cards from data/circuits.json
    - Opens a modal with SVG track & an animated car
    - Start / Stop / Reset + Speed slider (km/h)
    - No global click blocking; only gallery cards open the modal
*/

(function () {
  const gallery = document.getElementById('gallery');

  // Modal refs
  const backdrop = document.getElementById('backdrop');
  const modal = document.getElementById('pop');
  const cName = document.getElementById('cName');
  const cSub = document.getElementById('cSub');
  const trackHero = document.getElementById('trackHero');

  const kerb = document.getElementById('kerb');
  const asphalt = document.getElementById('asphalt');
  const line = document.getElementById('line');
  const measure = document.getElementById('measure');
  const car = document.getElementById('car');

  const stLen = document.getElementById('stLen');
  const stLaps = document.getElementById('stLaps');
  const stFirst = document.getElementById('stFirst');
  const stDist = document.getElementById('stDist');
  const stRecord = document.getElementById('stRecord');

  const lapTimeEl = document.getElementById('lapTime');
  const bestTimeEl = document.getElementById('bestTime');
  const lapsTable = document.getElementById('lapsTable').querySelector('tbody');

  const startBtn = document.getElementById('startBtn');
  const stopBtn = document.getElementById('stopBtn');
  const resetBtn = document.getElementById('resetBtn');
  const closeBtn = document.getElementById('closeBtn');
  const speedSlider = document.getElementById('speed');
  const speedLabel = document.getElementById('speedLabel');

  let circuits = [];
  let current = null;

  // Animation state
  let raf = 0;
  let running = false;
  let pxLen = 0;            // total path length in px
  let kmLen = 0;            // real length in km (from JSON)
  let pxPerSec = 0;
  let progressPx = 0;       // current distance travelled along path in px
  let prevTimestamp = 0;
  let lapStartTime = 0;
  let laps = [];
  let best = null;

  speedSlider.addEventListener('input', () => {
    speedLabel.textContent = `${speedSlider.value} km/h`;
    computeSpeed();
  });

  function computeSpeed() {
    if (!pxLen || !kmLen) return;
    const speedKmh = Number(speedSlider.value);
    // px per second = (km/h * pxLen / kmLen) / 3600
    pxPerSec = (speedKmh * (pxLen / kmLen)) / 3600;
  }

  // -------- Fetch and render cards --------
  async function load() {
    const res = await fetch('data/circuits.json?v=' + Date.now(), { cache: 'no-store' });
    circuits = await res.json();

    gallery.innerHTML = circuits.map(c => {
      const img = (c.image || '').trim();
      const src = img ? `img/circuits/${encodeURIComponent(img)}` : '';
      const alt = c.name ? `${c.name} image` : 'Circuit image';
      return `
        <article class="card" data-id="${c.id}" tabindex="0" role="button" aria-label="${c.name}">
          ${src ? `<img class="thumb" src="${src}" alt="${alt}" loading="lazy" />`
                : `<div class="thumb" aria-hidden="true"></div>`}
          <div class="card-body">
            <h4 style="margin:0 0 4px 0">${c.name}</h4>
            <div class="muted">${c.city}, ${c.country}</div>
            <div class="muted" style="margin-top:4px">${Number(c.length_km).toFixed(3)} km • ${c.laps} laps</div>
          </div>
        </article>
      `;
    }).join('');
  }

  // Only handle clicks inside the gallery
  gallery.addEventListener('click', (e) => {
    const card = e.target.closest('.card[data-id]');
    if (!card) return;
    const data = circuits.find(x => x.id === card.dataset.id);
    if (data) openCircuit(data);
  });
  // keyboard activation
  gallery.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' || e.key === ' ') {
      const card = e.target.closest('.card[data-id]');
      if (!card) return;
      e.preventDefault();
      const data = circuits.find(x => x.id === card.dataset.id);
      if (data) openCircuit(data);
    }
  });

  // -------- Modal open/close --------
  function openCircuit(c) {
    current = c;
    cName.textContent = c.name;
    cSub.textContent = `${c.city}, ${c.country}`;

    const hero = (c.hero || c.image || '').trim();
    trackHero.src = hero ? `img/circuits/${encodeURIComponent(hero)}` : '';
    trackHero.style.display = hero ? 'block' : 'none';

    // stats
    kmLen = Number(c.length_km) || 0;
    stLen.textContent = kmLen.toFixed(3);
    stLaps.textContent = c.laps ?? '—';
    stFirst.textContent = c.first_gp_year ?? '—';
    stDist.textContent = (c.race_distance_km ?? (kmLen * (c.laps ?? 0))).toFixed(3);
    stRecord.textContent = c.lap_record ? `${c.lap_record.time} — ${c.lap_record.driver} (${c.lap_record.year})`
                                        : (c.lap_record?.time || '—');

    // track path (fallback to a nice oval)
    const pathD = (c.svgPath || '').trim() || ovalPath();
    kerb.setAttribute('d', pathD);
    asphalt.setAttribute('d', pathD);
    line.setAttribute('d', pathD);
    measure.setAttribute('d', pathD);

    // path length in px
    pxLen = measure.getTotalLength();
    progressPx = 0;
    laps = [];
    best = null;
    renderLaps();
    lapTimeEl.textContent = '0.0';
    bestTimeEl.textContent = '—';

    computeSpeed();
    positionCar(0);

    // open
    modal.classList.add('open');
    backdrop.classList.add('open');

    // make sure backdrop doesn't block header when closed (safety on open/close handled)
  }

  function closeModal() {
    stop();
    modal.classList.remove('open');
    backdrop.classList.remove('open');
  }

  document.getElementById('closeBtn').addEventListener('click', closeModal);
  backdrop.addEventListener('click', closeModal);
  window.addEventListener('keydown', (e) => { if (e.key === 'Escape') closeModal(); });

  // -------- Animation controls --------
  function start() {
    if (running) return;
    running = true;
    prevTimestamp = performance.now();
    if (laps.length === 0) lapStartTime = prevTimestamp;
    raf = requestAnimationFrame(step);
  }
  function stop() {
    running = false;
    if (raf) cancelAnimationFrame(raf);
  }
  function reset() {
    stop();
    progressPx = 0;
    laps = [];
    best = null;
    renderLaps();
    lapTimeEl.textContent = '0.0';
    bestTimeEl.textContent = '—';
    positionCar(0);
  }
  startBtn.addEventListener('click', start);
  stopBtn.addEventListener('click', stop);
  resetBtn.addEventListener('click', reset);

  function step(ts) {
    if (!running) return;
    const dt = (ts - prevTimestamp) / 1000; // seconds
    prevTimestamp = ts;

    progressPx += pxPerSec * dt;

    // wrap & lap timing
    if (progressPx >= pxLen) {
      progressPx = progressPx % pxLen;
      const now = ts;
      const lapTime = (now - lapStartTime) / 1000;
      lapStartTime = now;
      laps.push(lapTime);
      if (best === null || lapTime < best) best = lapTime;
      renderLaps();
      bestTimeEl.textContent = best.toFixed(3);
    }

    // current lap time live
    lapTimeEl.textContent = ((ts - lapStartTime) / 1000).toFixed(1);

    positionCar(progressPx);
    raf = requestAnimationFrame(step);
  }

  function positionCar(lenPx) {
    if (!pxLen) return;
    const p = measure.getPointAtLength(lenPx);
    const p2 = measure.getPointAtLength(Math.min(pxLen, lenPx + 1));
    const angle = Math.atan2(p2.y - p.y, p2.x - p.x) * 180 / Math.PI;
    car.setAttribute('transform', `translate(${p.x},${p.y}) rotate(${angle})`);
  }

  function renderLaps() {
    if (!laps.length) {
      lapsTable.innerHTML = `<tr><td colspan="2" class="muted">No laps yet.</td></tr>`;
      return;
    }
    lapsTable.innerHTML = laps.map((t,i) => {
      const cls = (best !== null && Math.abs(t - best) < 1e-9) ? ' class="best"' : '';
      return `<tr${cls}><td>${i+1}</td><td>${t.toFixed(3)}</td></tr>`;
    }).join('');
  }

  // A clean oval fallback path inside the 1000x600 viewBox
  function ovalPath() {
    const x = 120, y = 120, w = 760, h = 360, r = 160;
    // simplified smooth oval
    return `M ${x+r} ${y}
            H ${x+w-r}
            C ${x+w} ${y} ${x+w} ${y} ${x+w} ${y+r}
            V ${y+h-r}
            C ${x+w} ${y+h} ${x+w} ${y+h} ${x+w-r} ${y+h}
            H ${x+r}
            C ${x} ${y+h} ${x} ${y+h} ${x} ${y+h-r}
            V ${y+r}
            C ${x} ${y} ${x} ${y} ${x+r} ${y}
            Z`.replace(/\s+/g,' ');
  }

  // init
  load();
})();
