const saveDialog = document.getElementById('entity-dialog');

document.getElementById('add-button').addEventListener('click', () => {
    resetEntityDialog();
    document.getElementById('add-entity-button').style.display = '';
    document.getElementById('add-entity-button').disabled = false;
    document.getElementById('entity-fieldset').disabled = false;
    saveDialog.showModal();
});

document.getElementById('list-button').addEventListener('click', () => {
    loadEntities();
});

saveDialog.addEventListener('close', (e) => {
    if (["PUT","POST","DELETE"].indexOf(saveDialog.returnValue) < 0 || !document.forms['entity-form'].checkValidity()) {
        //saveDialog.reportValidity();
        return;
    };
    var entity = getEntityJson();
    fetch(api + ((entity.id === '') ? '' : '/' + entity.id), {
        method: saveDialog.returnValue,
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
    document.getElementById('add-entity-button').style.display = 'none';
    document.getElementById('add-entity-button').disabled = true;
    document.getElementById('edit-entity-button').style.display = 'none';
    document.getElementById('edit-entity-button').disabled = true;
    document.getElementById('delete-entity-button').style.display = 'none';
    document.getElementById('delete-entity-button').disabled = true;
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
    document.getElementById('edit-entity-button').style.display = '';
    document.getElementById('edit-entity-button').disabled = false;
    document.getElementById('entity-fieldset').disabled = false;
}

function deleteEntity(id) {
    showEntity(id);
    document.getElementById('delete-entity-button').style.display = '';
    document.getElementById('delete-entity-button').disabled = false;
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