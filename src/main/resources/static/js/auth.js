// Tiny auth helper for Aj F1 Live
// Works with backend endpoints:
//   GET  /api/auth/me          -> { loggedIn: boolean, name, email, role? }
//   POST /api/auth/logout      -> { ok: true }

const STORAGE_KEY = "auth.me";

/** Persist last-known session snapshot (for instant UI) */
function setCache(me) {
  try { sessionStorage.setItem(STORAGE_KEY, JSON.stringify(me)); } catch {}
}
function getCache() {
  try { return JSON.parse(sessionStorage.getItem(STORAGE_KEY) || "null"); } catch { return null; }
}

/** Fetch session state from server */
async function fetchMe() {
  const res = await fetch("/api/auth/me", { credentials: "same-origin" });
  if (!res.ok) return { loggedIn: false };
  return await res.json();
}

/** Public API */
export const Auth = {
  /** Returns cached user (may be null or {loggedIn:false}) */
  get() {
    const me = getCache();
    return me && me.loggedIn ? me : null;
  },

  /** Refresh from server and cache the result */
  async refresh() {
    const me = await fetchMe();
    setCache(me);
    return me;
  },

  /** Logout on server and clear cache */
  async logout() {
    try { await fetch("/api/auth/logout", { method: "POST", credentials: "same-origin" }); }
    finally { setCache({ loggedIn: false }); }
  }
};

/** Mount navbar widgets:
 *  - Fills #welcome with "Welcome, <name>"
 *  - Toggles #authLink to Logout (with click handler) when logged in
 *  - Hides duplicate Register links if logged in
 */
export async function mountNavbar() {
  // allow duplicate ids in markup (we'll handle all of them)
  const $all = (sel) => Array.from(document.querySelectorAll(sel));
  const $ = (sel) => document.querySelector(sel);

  const welcomeEls = $all("#welcome");
  const authEls    = $all("#authLink");
  const regEls     = $all("#registerLink");

  // show cached state immediately (fast paint)
  const cached = Auth.get();
  if (cached) paintNavbar(cached);

  // then get authoritative state
  const me = await Auth.refresh();
  paintNavbar(me);

  function paintNavbar(meState) {
    const loggedIn = !!meState?.loggedIn;

    // Welcome text
    const name = meState?.name || meState?.displayName || meState?.email || "";
    welcomeEls.forEach(el => { el.textContent = loggedIn ? `Welcome, ${name}` : ""; });

    // Toggle Login ↔ Logout
    authEls.forEach(el => {
      if (!el) return;
      if (loggedIn) {
        el.textContent = "Logout";
        el.href = "#";
        el.onclick = async (e) => {
          e.preventDefault();
          await Auth.logout();
          // keep UX simple: back to home and refresh UI
          if (location.pathname !== "/index.html" && location.pathname !== "/") {
            location.href = "/index.html";
          } else {
            // repaint navbar + hero greeting
            paintNavbar({ loggedIn: false });
            // also clear hero greeting if present
            const hi = document.getElementById("hiUser");
            if (hi) hi.textContent = "";
          }
        };
      } else {
        el.textContent = "Login";
        el.href = "/login.html";
        el.onclick = null;
      }
    });

    // Hide Register link(s) when logged in; show when logged out
    regEls.forEach(el => {
      if (!el) return;
      el.style.display = loggedIn ? "none" : "";
    });

    // Also populate hero greeting if the page has #hiUser
    const hi = document.getElementById("hiUser");
    if (hi) hi.textContent = loggedIn ? ` ${name}` : "";
  }
}
