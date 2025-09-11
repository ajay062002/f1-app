import { auth } from "../api.js";

export const LoginPage = {
  async render() {
    return `
      <section class="max-w-md mx-auto bg-neutral-900 p-6 rounded-2xl shadow">
        <h1 class="text-2xl font-semibold mb-4">Login</h1>
        <form id="loginForm" class="space-y-4">
          <input name="username" class="w-full bg-neutral-800 rounded p-2" placeholder="Username"/>
          <input name="password" type="password" class="w-full bg-neutral-800 rounded p-2" placeholder="Password"/>
          <button class="w-full bg-red-600 hover:bg-red-500 rounded p-2">Login</button>
        </form>
      </section>
    `;
  },
  async afterRender() {
    const form = document.getElementById("loginForm");
    form.addEventListener("submit", (e) => {
      e.preventDefault();
      const data = new FormData(form);
      auth.login(data.get("username"), data.get("password"));
      window.location.hash = "#/admin";
    });
  }
};
