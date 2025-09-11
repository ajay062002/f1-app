import { auth } from "../api.js";

export const AdminPage = {
  async render() {
    return `
      <section>
        <div class="flex justify-between">
          <h1 class="text-2xl font-semibold">Admin</h1>
          <button id="logoutBtn" class="bg-neutral-800 px-3 py-1 rounded">Logout</button>
        </div>
        <p>Welcome, <span class="text-red-400">${auth.user()}</span>!</p>
      </section>
    `;
  },
  async afterRender() {
    document.getElementById("logoutBtn").addEventListener("click", () => {
      auth.logout();
      window.location.hash = "#/login";
    });
  }
};
