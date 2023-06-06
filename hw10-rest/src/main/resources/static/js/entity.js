const api = document.getElementById('api').value;
const saveDialog = document.getElementById('entity-dialog');

document.getElementById('add-button').addEventListener('click', () => {
    resetEntityDialog();
    document.getElementById('add-entity-button').style.display = '';
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
    var entity = {
        id: document.getElementById('entity.id').value,
        name: document.getElementById('entity.name').value
    };
    fetch(api + ((entity.id === '') ? '' : '/' + entity.id), {
        method: saveDialog.returnValue,
        headers: {
            'accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entity)
    })
    .then(response => {
        if (response.status === 200) {
            loadEntities();
        } else {
            console.log(response.text());
            document.getElementById('entity-result').innerHTML = response.status;
            saveDialog.showModal();
        }
    });
});

function resetEntityDialog() {
    document.forms['entity-form'].reset();
    document.getElementById('add-entity-button').style.display = 'none';
    document.getElementById('edit-entity-button').style.display = 'none';
    document.getElementById('delete-entity-button').style.display = 'none';
    document.getElementById('entity-result').innerHTML = '';
    document.getElementById('entity-fieldset').disabled = true;
    document.getElementById('entity.id').value = '';
}

function showEntity(id) {
    resetEntityDialog();
    fetch(api + '/' + id)
        .then(response => response.json())
        .then(json => {
            document.getElementById('entity.id').value = json.id;
            document.getElementById('entity.name').value = json.name;
            saveDialog.showModal();
        })
        .catch((error) => {
            console.log(error);
        });
}

function editEntity(id) {
    showEntity(id);
    document.getElementById('edit-entity-button').style.display = '';
    document.getElementById('entity-fieldset').disabled = false;
}

function deleteEntity(id) {
    showEntity(id);
    document.getElementById('delete-entity-button').style.display = '';
}

function loadEntities() {
    fetch(api)
        .then(response => response.json())
        .then(json => {
            let rows = '';
            json.map(row => {
                rows += `
                    <tr>
                        <td>${row.name}</td>
                        <td>
                            <button onclick='editEntity("${row.id}")'>Править</button>
                            <button onclick='deleteEntity("${row.id}")'>Удалить</button>
                        </td>
                    </tr>`;
            })
            document.getElementById('entities').innerHTML = rows;
        })
        .catch((error) => {
            console.log(error);
        });
}

window.onload = function() {
    loadEntities();
}