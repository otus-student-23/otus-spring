var granted = false;

switch (Notification.permission) {
    case "granted":
        granted = true;
        break;
    case "denied":
        console.error("Уведомления отключены");
        break;
    case "default":
        granted = Notification.requestPermission() === 'granted' ? true : false;
}

const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + location.host + '/notification'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe(topic, (e) => {
        let json = JSON.parse(e.body);
        console.log(json);
        let entities = document.getElementById("entities");
        if (document.getElementById(json.entity.id)) {
            entities.removeChild(document.getElementById(json.entity.id));
        }
        if (json.event !== 'DELETE') {
            entities.innerHTML += buildEntityRow(json.entity);
        }
        if (granted) {
            let jsonNotice = toJsonNotice(json);
            var notification = new Notification(jsonNotice.subj, {
                //tag : "",
                body : jsonNotice.body,
                icon : "/favicon.ico"
            });
            setTimeout(function() {notification.close();}, 3000);
        }
    });
};

stompClient.onWebSocketError = (error) => {
    console.error(error);
};

stompClient.onStompError = (frame) => {
    console.error(frame.headers['message']);
    console.error(frame.body);
};

function disconnect() {
    stompClient.deactivate();
    console.log("Disconnected");
}

function sendConfirm() {
    stompClient.publish({
        destination: "/notification/confirm",
        body: JSON.stringify({'status': 'done'})
    });
}

stompClient.activate();