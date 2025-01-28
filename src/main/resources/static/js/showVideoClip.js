const app = Vue.createApp({
    data() {
        return {
            videoClipWithComments: videoClipWithComments || null,
            user: user || null,
            hasLiked: hasLiked,
            hasDisliked: hasDisliked,
            newComment: ''
        };
    },
    computed: {
        canUserReact() {
            return this.user != null && (user.role === "ROLE_ADMIN" || user.role === "ROLE_RATER");
        },
        userLoggedIn() {
            return this.user != null;
        },
        releaseDateSortedComments() {
            if (!this.videoClipWithComments || !this.videoClipWithComments.comments) {
                return [];
            }

            return [...this.videoClipWithComments.comments].sort((a, b) => {
                if (!a.createdTime && !b.createdTime) {
                    return 0;
                } else if (!a.createdTime) {
                    return 1; // Null values are considered older
                } else if (!b.createdTime) {
                    return -1; // Null values are considered older
                }

                return new Date(b.createdTime) - new Date(a.createdTime); // Newest first
            });
        },
    },
    methods: {
        likeVideo() {
            if (!this.userLoggedIn) {

                this.requestLogin();
                return;
            }

            if (!this.canUserReact)
                return;

            axios
                .post("/api/reactions/like", {
                        videoClipId: this.videoClipWithComments.uuid,
                    },
                    {
                        headers: {
                            "Content-Type": "application/json", // Explicitly setting Content-Type
                        },
                    })
                .then((response) => {
                    console.log(response.data.liked);
                    console.log(response.data.disliked);
                    console.log(response.data.videoClipWithComments);

                    this.hasLiked = response.data.liked;
                    this.hasDisliked = response.data.disliked; // Remove dislike if previously disliked
                    this.videoClipWithComments = response.data.videoClipWithComments;
                })
                .catch((error) => {
                    console.error(error.response?.data || error.message);
                });
        },
        dislikeVideo() {
            if (!this.userLoggedIn) {

                this.requestLogin();
                return;
            }

            if (!this.canUserReact)
                return;

            axios
                .post("/api/reactions/dislike", {
                        videoClipId: this.videoClipWithComments.uuid,
                    },
                    {
                        headers: {
                            "Content-Type": "application/json", // Explicitly setting Content-Type
                        },
                    })
                .then((response) => {

                    this.hasLiked = response.data.liked;
                    this.hasDisliked = response.data.disliked; // Remove dislike if previously disliked
                    this.videoClipWithComments = response.data.videoClipWithComments;
                })
                .catch((error) => {
                    console.error(error.response?.data || error.message);
                });
        },
        formatDate(dateString) {
            if (!dateString) return '';
            const date = new Date(dateString);
            return date.toLocaleDateString('el-GR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
            });
        },
        formatCommentDate(dateString) {
            if (!dateString) return '';

            const date = new Date(dateString);
            return date.toLocaleString('el-GR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                hour12: false  // 24-hour format; set to true for 12-hour format
            });
        },
        requestLogin() {
            // Trigger the login modal in App 1
            window.dispatchEvent(new CustomEvent('trigger-login'));
        },
        submitComment() {
            if (!this.newComment.trim()) return;

            if (!this.userLoggedIn) {
                this.requestLogin();
                return;
            }

            if (!this.canUserReact) return;

            // Prepare form data for the request
            const formData = new URLSearchParams();
            formData.append("videoClipId", this.videoClipWithComments.uuid);
            formData.append("content", this.newComment);

            axios.post("/api/comments/newComment", formData, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            })
            .then((response) => {
                if (!response.data.success) {
                    console.error("Failed to post comment:", response.data.message);
                    return
                }

                // Add the new comment to the top of the list
                this.videoClipWithComments.comments.unshift({
                    uuid: Date.now(),  // Assuming the backend doesn't return the new comment
                    user: {
                        username: this.user.username,
                        role: this.user.role
                    },
                    content: this.newComment,
                    createdTime: new Date().toISOString()
                });

                // Clear the comment input
                this.newComment = '';
            })
            .catch((error) => {
                console.error("Error posting comment:", error.response?.data || error.message);
            });
        }

    },
    mounted() {
        // Initialize hasLiked and hasDisliked based on the backend data
        // Example:
        // this.hasLiked = videoClipWithComments.hasLiked;
        // this.hasDisliked = videoClipWithComments.hasDisliked;
    },
});

app.mount('#showVideoClip');
