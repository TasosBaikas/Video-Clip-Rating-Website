const headerApp = Vue.createApp({
    data() {
        return {
            user: {
                username: null,
                loggedIn: false,
            },
            loginForm: {
                username: '',
                password: '',
            },
            signUpForm: {
                username: '',
                password: '',
                role: 'Επισκέπτης',
            },
            showPopup: false,
            showSignUp: false,
            loginError: '',
            signUpError: '',
            loading: {
                login: false,
                signUp: false,
            },
        };
    },
    methods: {
        togglePopup() {
            this.showPopup = !this.showPopup;
        },
        toggleSignUp() {
            this.showSignUp = !this.showSignUp;
        },
        reloadPage(){
            window.location.reload();
        },
        initializeUser() {
            const username = localStorage.getItem('username');
            if (username) {
                this.user.username = username;
                this.user.loggedIn = true;
            } else {
                this.user.username = null;
                this.user.loggedIn = false;
            }
        },
        loginUser() {
            this.loading.login = true;
            this.loginError = '';

            const formData = new URLSearchParams();
            formData.append('username', this.loginForm.username);
            formData.append('password', this.loginForm.password);

            axios.post('/login', formData, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(response => {
                    this.loading.login = false;
                    if (!response.data) {
                        this.loginError = 'Λάθος στοιχεία';
                        return;
                    }

                    this.user.loggedIn = true;
                    this.reloadPage();
            })
            .catch(error => {
                this.loading.login = false;
                this.loginError = error.response ? error.response.data.message : 'An error occurred';
            });
        },
        signUpUser() {
            this.loading.signUp = true;
            this.signUpError = '';
            axios.post('/signUp', this.signUpForm)
                .then(response => {
                    this.loading.signUp = false;
                    if (!response.data.success) {
                        this.signUpError = response.data.message;
                        return;
                    }

                    this.loginForm.username = this.signUpForm.username
                    this.loginForm.password = this.signUpForm.password
                    this.loginUser()
                })
                .catch(error => {
                    this.loading.signUp = false;
                    this.signUpError = error.response ? error.response.data.message : 'An error occurred';
                });
        },
        logoutUser() {
            axios.post('/logout', null, {
                headers: {
                    'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
                }
            }).then((response) => {

                    this.user.username = null;
                    this.user.loggedIn = false;
                    this.reloadPage(); // Optional: Reload the page to refresh the UI
                })
                .catch(error => {
                    console.error('Logout failed', error);
                });
        },
        handleProfileButton() {
            if (!this.user.loggedIn) {
                this.togglePopup();
            } else {
                const profileUrl = `/UserProfile/Index?username=${encodeURIComponent(this.user.username)}`;
                window.location.href = profileUrl;
            }
        },
        profileRedirect() {
            if (!this.user.loggedIn) {
                this.togglePopup(); // Opens the login/signup popup if the user is not logged in.
            } else {
                const profileUrl = `/UserProfile/Index?username=${encodeURIComponent(this.user.username)}`;
                window.location.href = profileUrl; // Redirects to the user's profile page if logged in.
            }
        }
    },
    mounted() {
        this.initializeUser();
    },
});

headerApp.mount('#header');
