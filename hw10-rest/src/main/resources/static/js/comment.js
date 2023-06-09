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
    fetch('/api/book/' + (new URLSearchParams(window.location.search)).get('bookId'))
        .then(response => response.json())
        .then(json => {
            document.getElementById('book').value = json.name;
            document.getElementById('author').value = json.author.name;
        })
        .catch((error) => {
            console.log(error);
        });

    let rows = '';
    json.map(row => {
        rows += `
            <tr>
                <td><pre>${row.comment}</pre></td>
                <td>
                    <button onclick='editEntity("${row.id}")'>Править</button>
                    <button onclick='deleteEntity("${row.id}")'>Удалить</button>
                </td>
            </tr>`;
    })
    return rows;
}