console.log("Script JS chargé"); // ligne à ajouter

document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/movies')
        .then(response => response.json())
        .then(movies => {
            console.log("Movies récupérés :", movies); // debug
            const moviesDiv = document.getElementById('movies');
            movies.forEach(movie => {
                const movieDiv = document.createElement('div');
                movieDiv.className = 'movie';
                movieDiv.innerHTML = `
                    <h2>${movie.title}</h2>
                    <p>Genre: ${movie.genre}</p>
                    <p>Year: ${movie.year}</p>
                `;
                moviesDiv.appendChild(movieDiv);
            });
        })
        .catch(error => console.error('Erreur lors de la récupération des films:', error));
});
