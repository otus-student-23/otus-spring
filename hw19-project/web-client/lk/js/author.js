const api = '/api/library/author';
const topic = '/topic/author';

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
                <button onclick='editEntityDialog("${entity.id}")'>Править</button>
                <button onclick='deleteEntityDialog("${entity.id}")'>Удалить</button>
            </td>
        </tr>`;
}

function toJsonNotice(event) {
    return {
        subj: ((event.event === 'INSERT') ? "Добавлен" : ((event.event === 'UPDATE') ? "Изменен" : "Удален")) + " автор",
        body: event.entity.name
    };
}