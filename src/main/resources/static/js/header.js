const showPopupBtn = document.querySelector(".login-btn");
const formPopup = document.querySelector(".form-popup");
const hidePopupBtn = document.querySelector(".form-popup .close-btn");
const loginSignupLink = document.querySelectorAll(".form-box .bottom-link a");


$(document).ready(function () {

    initializeProfileButton();

    initializeLoginForm();
    initializeSignUpForm();
});



function initializeProfileButton() {
    // Get the username from local storage

    var username = localStorage.getItem('username');

    $('#login-profile-button').css('visibility', 'visible');

    if (!username || username == "") {
        // If username does not exist, show 'Login' text

        $('#login-profile-button').text('Login')

        showPopupBtn.addEventListener("click", () => {
            document.body.classList.toggle("show-popup");
        });

        loginSignupLink.forEach(link => {
            link.addEventListener("click", (e) => {
                e.preventDefault();
                formPopup.classList[link.id === "signup-link" ? 'add' : 'remove']("show-signup");
            });
        });

        hidePopupBtn.addEventListener("click", () => showPopupBtn.click());

        return;
    }


    // Set the username in the navbar
    const profileUrl = '/UserProfile/Index?username=' + encodeURIComponent(username);

    $('#login-profile-button').text("Profile");
    $('#login-profile-button').attr('onclick', 'window.location.href="' + profileUrl + '"');

    // Hide the login button
    console.log("Profile button initialized");
}


function getBaseUrl() {
    return window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port : '');
}

function initializeLoginForm() {

    $('#loginForm').on('submit', function (event) {

        $('#loading-spinner-login').css('visibility', 'visible');

        event.preventDefault(); // Prevent the form from submitting via the browser

        console.log("Form submission intercepted");


        $.ajax({
            url: getBaseUrl() + '/login', // Use a valid URL for testing
            type: 'POST',
            data: $(this).serialize(),
            success: function (result) {

                $('#loading-spinner-login').css('visibility', 'hidden');

                if (!result.success) {

                    $('#failed-login-message').text(result.message)
                    return;
                }

                localStorage.setItem('username', result.user.USERNAME);

                window.location.reload();
            },
            error: function (xhr, status, error) {

                $('#loading-spinner-login').css('visibility', 'hidden');


                console.log("AJAX error login: ", error);
                $('#failed-login-message').text(error)


            }
        });
    });

    console.log("Login form initialized");
}

function initializeSignUpForm() {

    $('#signUpForm').on('submit', function (event) {

        $('#loading-spinner-sign-up').css('visibility', 'visible');

        event.preventDefault(); // Prevent the form from submitting via the browser

        console.log("Sign-up form submission intercepted");


        $.ajax({
            url: getBaseUrl() + '/SignUp', // Use a valid URL for testing
            type: 'POST',
            data: $(this).serialize(),
            success: function (result) {
                $('#loading-spinner-sign-up').css('visibility', 'hidden');

                if (!result.success) {
                    $('#failed-sign-up-message').text(result.message);
                    return;
                }


                localStorage.setItem('username', result.user.USERNAME);

                window.location.reload();
            },
            error: function (xhr, status, error) {
                $('#loading-spinner-sign-up').css('visibility', 'hidden');

                console.log("AJAX error sign-up: ", error);
                $('#failed-sign-up-message').text("Error: " + error);
            }
        });
    });

    console.log("Sign-up form initialized");
}