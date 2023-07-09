function addAuthDialog() {
    document.querySelector('body').insertAdjacentHTML('beforeend', `
        <dialog id="auth-dialog">
            <form method="dialog">
            <fieldset>
                <legend>Вход</legend>
                <p>
                    <label for="username">Пользователь</label>
                    <input type="text" id="username" required autofocus>
                </p>
                <p>
                    <label for="password">Пароль</label>
                    <input type="password" id="password" required autocomplete="on">
                </p>
            </fieldset>
            <hr>
            <button type="submit">Войти</button>
            </form>
        </dialog>`
    );

    document.getElementById('auth-dialog').addEventListener('cancel', (event) => {
        event.preventDefault();
    });

    document.getElementById('auth-dialog').addEventListener('close', (event) => {
        fetch('/login.html', {
                method: 'POST',
                headers: {
                    'accept': '*/*',
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'username=' + document.getElementById('username').value
                        + '&password=' + document.getElementById('password').value
            }).then(response => {
                if (response.url.includes("error")) {
                    document.getElementById('auth-dialog').showModal();
                }
            });
    });
}
