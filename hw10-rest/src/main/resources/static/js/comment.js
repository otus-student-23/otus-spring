const api = '/api/book/' + (new URLSearchParams(window.location.search)).get('bookId') + '/comment';

function getEntityJson() {
    return {
        id: document.getElementById('entity.id').value,
        book: {
            id: document.getElementById('book.id').value
        },
        comment: document.getElementById('entity.comment').value
    };
}

function fillEntityForm(json) {
    document.getElementById('entity.id').value = json.id;
    document.getElementById('book.id').value = json.book.id;
    document.getElementById('entity.comment').value = json.comment;
}

function getEntitiesRows(json) {
    let rows = '';
    json.map(row => {
        rows += `
            <tr>
                <td>${row.comment}</td>
                <td>
                    <button onclick='editEntity("${row.id}")'>Править</button>
                    <button onclick='deleteEntity("${row.id}")'>Удалить</button>
                </td>
            </tr>`;
    })
    return rows;
}