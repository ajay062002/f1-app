import { auth } from "./api.js";
import { HomePage } from "./pages/home.js";
import { LoginPage } from "./pages/login.js";
import { DriversPage } from "./pages/drivers.js";
import { TeamsPage } from "./pages/teams.js";
import { StandingsPage } from "./pages/standings.js";
import { AdminPage } from "./pages/admin.js";

const routes = {
  "/": HomePage,
  "/login": LoginPage,
  "/drivers": DriversPage,
  "/teams": TeamsPage,
  "/standings": StandingsPage,
  "/admin": AdminPage,
};

function parseRoute() {
  const hash = window.location.hash || "#/";
  const path = hash.replace(/^#/, "");
  return path || "/";
}

async function render() {
  const app = document.getElementById("app");
  const path = parseRoute();

  // Guard admin
  if (path.startsWith("/admin") && !auth.isAuthed()) {
    window.location.hash = "#/login";
    return;
  }

  const Page = routes[path] || HomePage;
  app.innerHTML = await Page.render();
  await Page.afterRender?.();
}

window.addEventListener("hashchange", render);
window.addEventListener("load", render);
