export const TeamsPage = {
  async render() {
    return `
      <section>
        <h1 class="text-2xl font-semibold mb-4">Teams</h1>
        <div class="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
          <div class="bg-neutral-900 p-5 rounded">Red Bull Racing</div>
          <div class="bg-neutral-900 p-5 rounded">Ferrari</div>
          <div class="bg-neutral-900 p-5 rounded">Mercedes</div>
          <div class="bg-neutral-900 p-5 rounded">McLaren</div>
        </div>
      </section>
    `;
  }
};
