function addAuthDialog() {
    document.querySelector('body').insertAdjacentHTML('beforeend', `
        <dialog id="auth-dialog">
            <iframe id="auth-iframe" src="/lk/ping.html" width="100%" height="100%"/>
        </dialog>`
    );

    document.getElementById('auth-dialog').addEventListener('cancel', (event) => {
        event.preventDefault();
    });
}
