const api = '/api/genre';

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

function getEntitiesRows(json) {
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
    return rows;
}