const api = '/api/book/' + (new URLSearchParams(window.location.search)).get('bookId') + '/comment';
const topic = '/topic/book_comment';

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
    fetch('/api/book/' + (new URLSearchParams(window.location.search)).get('bookId'), {redirect: 'manual'})
        .then(response => response.json())
        .then(json => {
            document.getElementById('book').value = json.name;
            document.getElementById('author').value = json.author.name;
        })
        .catch((error) => {
            console.log(error);
        });
}

function buildEntityRow(entity) {
    if (typeof entity.book_id === "undefined" || (new URLSearchParams(window.location.search)).get('bookId') === entity.book_id)
        return `
            <tr id='${entity.id}'>
                <td><pre>${entity.comment}</pre></td>
                <td>
                    <button onclick='editEntityDialog("${entity.id}")'>Править</button>
                    <button onclick='deleteEntityDialog("${entity.id}")'>Удалить</button>
                </td>
            </tr>`;
    else return '';
}
