const saveDialog = document.getElementById('entity-dialog');
const submitButton = document.getElementById('submit-button');

document.getElementById('add-button').addEventListener('click', () => {
    resetEntityDialog();
    submitButton.value = 'POST';
    submitButton.innerHTML = 'Добавить';
    document.getElementById('entity-fieldset').disabled = false;
    saveDialog.showModal();
});

document.getElementById('list-button').addEventListener('click', () => {
    loadEntities();
});

saveDialog.addEventListener('close', (e) => {
    var httpMethod = saveDialog.returnValue;
    saveDialog.returnValue = "";
    if (["PUT","POST","DELETE"].indexOf(httpMethod) < 0 || !document.forms['entity-form'].checkValidity()) {
        //saveDialog.reportValidity();
        return;
    };
    var entity = getEntityJson();
    fetch(api + ((entity.id === '') ? '' : '/' + entity.id), {
        method: httpMethod,
        headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entity)
    })
        .then(response => parseResponse(response))
        .then(response => {
            if (response.status === 200) {
                loadEntities();
            } else {
                document.getElementById('entity-result').innerHTML = escapeHtml(response.data);
                saveDialog.showModal();
            }
        })
        .catch((error) => {
            console.log(error);
        });
});

function resetEntityDialog() {
    document.forms['entity-form'].reset();
    submitButton.value = '';
    submitButton.innerHTML = 'X';
    document.getElementById('entity-result').innerHTML = '';
    document.getElementById('entity-fieldset').disabled = true;
    document.getElementById('entity.id').value = '';
}

function showEntity(id) {
    resetEntityDialog();
    fetch(api + '/' + id)
        .then(response => response.json())
        .then(json => {
            fillEntityForm(json);
            saveDialog.showModal();
        })
        .catch((error) => {
            console.log(error);
        });
}

function editEntity(id) {
    showEntity(id);
    submitButton.value = 'PUT';
    submitButton.innerHTML = 'Сохранить';
    document.getElementById('entity-fieldset').disabled = false;
}

function deleteEntity(id) {
    showEntity(id);
    submitButton.value = 'DELETE';
    submitButton.innerHTML = 'Удалить';
}

function loadEntities() {
    fetch(api)
        .then(response => response.json())
        .then(json => {
            document.getElementById('entities').innerHTML = getEntitiesRows(json);
        })
        .catch((error) => {
            console.log(error);
        });
}

window.onload = function() {
    loadEntities();
}

function parseResponse(response) {
    return response.text().then(body => {
        return {
            status: response.status,
            data: body
        }
    })
}

function escapeHtml(unsafe) {
    return unsafe
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
}