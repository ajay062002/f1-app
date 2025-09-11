import { getJSON } from "../api.js";

export const DriversPage = {
  async render() {
    return `
      <section>
        <h1 class="text-2xl font-semibold mb-4">Drivers</h1>
        <div id="driversGrid" class="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6"></div>
      </section>
    `;
  },
  async afterRender() {
    const grid = document.getElementById("driversGrid");
    try {
      const drivers = await getJSON("/data/drivers.json");
      grid.innerHTML = drivers.map(d => `
        <article class="bg-neutral-900 rounded overflow-hidden shadow">
          <img src="/img/drivers/${d.image}" class="w-full h-56 object-cover">
          <div class="p-4">
            <h3 class="text-lg font-semibold text-red-400">${d.name}</h3>
            <p class="text-sm text-neutral-300">${d.team}</p>
          </div>
        </article>
      `).join("");
    } catch (e) {
      grid.innerHTML = `<p class="text-red-400">Error loading drivers: ${e.message}</p>`;
    }
  }
};
