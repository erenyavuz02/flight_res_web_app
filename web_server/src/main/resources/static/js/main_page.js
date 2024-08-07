// Check the user's session and redirect if not logged in





//variables
var passengers_count = [];

function checkSession() {
    fetch('api/user/checkSession')
        .then(response => response.text())
        .then(data => {
            if (data === 'Logged in') {
                //showPage('user_mng');
            } else {
                throw new Error('Not logged in');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            //window.location.href = 'login.html';
        });
}
//checkSession();


// Function to show a specific page
function showPage(pageId) {
    const pages = document.querySelectorAll('.page'); // Get all pages
    pages.forEach(page => {
        page.classList.remove('active'); // Remove the 'active' class from all pages
    });
    document.getElementById(pageId).classList.add('active'); // Add the 'active' class to the selected page
    window.scrollTo({
        top: 0,
        behavior: 'smooth'  // Smooth scrolling behavior
    });
}

// Handle back/forward browser navigation
window.addEventListener('hashchange', () => {
    const pageId = window.location.hash.substring(1); // Get the ID of the page from the URL hash
    showPage(pageId); // Show the selected page
});

// Optional: Handle initial page load
window.addEventListener('load', () => {
    const pageId = window.location.hash.substring(1) || 'reservation';
    // Scroll to the top of the page automatically

    showPage(pageId);
});


window.addEventListener( 'pageshow', function() {

});


function showCustomAlert(message) {
    document.getElementById('custom-alert-message').textContent = message;
    document.getElementById('custom-alert').style.display = 'flex';
}

function closeCustomAlert() {
    document.getElementById('custom-alert').style.display = 'none';
}