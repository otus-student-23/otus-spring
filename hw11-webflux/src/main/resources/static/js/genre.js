const api = '/api/genre';
const eventSource = new EventSource(api + '/stream');

function getEntityJson() {
    return {
        id: document.getElementById('entity.id').value,
        name: document.getElementById('entity.name').value
    };
}

function fillEntityForm(json) {
    document.getElementById('entity.id').value = json.id;
    document.getElementById('entity.name').value = json.name;
}

function getEntitiesRows(json) {}

function buildEntityRow(entity) {
    return `
        <tr id='${entity.id}'>
            <td>${entity.name}</td>
            <td>
                <button onclick='editEntity("${entity.id}")'>Править</button>
                <button onclick='deleteEntity("${entity.id}")'>Удалить</button>
            </td>
        </tr>`;
}
