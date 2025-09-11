// Simple helpers
export const getJSON = async (path) => {
  const res = await fetch(path, { cache: "no-store" });
  if (!res.ok) throw new Error(`Failed to fetch ${path}: ${res.status}`);
  return res.json();
};

// Fake auth (replace with real API later)
export const auth = {
  isAuthed() { return !!localStorage.getItem("auth_token"); },
  login(username, password) {
    localStorage.setItem("auth_token", "demo-token");
    localStorage.setItem("auth_user", username || "admin");
  },
  logout() {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("auth_user");
  },
  user() { return localStorage.getItem("auth_user") || "admin"; }
};
