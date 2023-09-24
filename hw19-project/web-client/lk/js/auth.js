//https://www.keycloak.org/docs/latest/securing_apps/index.html#_javascript_adapter
var keycloak;
fetch('/gateway/keycloak', {redirect: 'manual'})
    .then(response => response.json())
    .then(param => {
        keycloak = new Keycloak(param);
        //keycloak.init({onLoad: 'login-required'}).then(auth => {
        keycloak.init({
            onLoad: 'check-sso',
            checkLoginIframe: true,
            checkLoginIframeInterval: 5,
            silentCheckSsoRedirectUri: window.location.origin + '/lk/silent-check-sso.html',
            enableLogging: true
        }).then(auth => {
            if (auth) {
                console.log(keycloak.subject +': '+ JSON.stringify(keycloak.idTokenParsed, null, " "));
                //console.log('exp = ' + new Date(keycloak.idTokenParsed.exp * 1000));
                document.getElementById('user').innerHTML = keycloak.idTokenParsed.preferred_username;
            } else {
                //fetch('/gateway/logout', {redirect: 'manual'}).catch((e) => {console.error(e);});
                keycloak.login();
            }
        });

        setInterval(() => {
            keycloak.updateToken(280).then(function(refreshed) {
                //if (refreshed) { } else { }
            }).catch(function() {
                //if (keycloak.isTokenExpired(5))
                keycloak.login();
            });
        }, 60000);
    })
    .catch((error) => {
        console.error(error);
    });

//--- iframe
document.querySelector('body').insertAdjacentHTML('beforeend', `
    <dialog id="auth-dialog">
        <iframe id="auth-iframe" src="/lk/iframe-ping.html" width="100%" height="100%"/>
    </dialog>`
);

document.getElementById('auth-dialog').addEventListener('cancel', (event) => {
    event.preventDefault();
});

function logout() {
    keycloak.logout();
    keycloak.clearToken();
    //document.cookie = 'SESSION=; Path=/; SameSite=Lax; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    fetch('/gateway/logout', {redirect: 'manual'}).catch((e) => {console.error(e);});;
    return false;
}
