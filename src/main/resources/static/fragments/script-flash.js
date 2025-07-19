document.addEventListener('DOMContentLoaded', function () {
    let checkMessageInterval = setInterval(function () {
        let alertElement = document.getElementById('messageFlash');
        
        if (alertElement) {
            // Masquer le message après 5 secondes
            setTimeout(function () {
                alertElement.classList.remove('show');
                alertElement.classList.add('hide');
                
                // Rafraîchir la page après la disparition du message
                window.location.reload();
            }, 5000);
            
            // Arrêter la vérification une fois que l'élément est trouvé
            clearInterval(checkMessageInterval);
        }
    }, 100); // Vérification toutes les 100 ms
});
