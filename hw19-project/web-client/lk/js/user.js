const api = '/api/auth/user';

function getEntityJson() {
    return {
        id: document.getElementById('entity.id').value,
        username: document.getElementById('entity.username').value,
        roles: document.getElementById('entity.roles').value
    };
}

function fillEntityForm(json) {
    document.getElementById('entity.id').value = json.id;
    document.getElementById('entity.username').value = json.username;
    document.getElementById('entity.roles').value = json.roles;
}

function getEntitiesRows(json) {}

function buildEntityRow(entity) {
    return `
        <tr id='${entity.id}'>
            <td>${entity.id}</td>
            <td>${entity.username}</td>
            <td>${entity.roles}</td>
            <td>
                <button onclick='editEntityDialog("${entity.id}")'>Править</button>
                <button onclick='deleteEntityDialog("${entity.id}")'>Удалить</button>
            </td>
        </tr>`;
}
