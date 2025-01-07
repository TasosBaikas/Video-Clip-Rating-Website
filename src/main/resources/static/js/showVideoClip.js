const app = Vue.createApp({
    data() {
        return {
            videoClipWithComments: videoClipWithComments || null,
            user: user || null,
            hasLiked: hasLiked,
            hasDisliked: hasDisliked,
        };
    },
    computed: {
        canUserReact() {
            return this.user != null && (user.role === "ROLE_ADMIN" || user.role === "ROLE_RATER");
        },
    },
    methods: {
        likeVideo() {
            if (!this.canUserReact) return;

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
            if (!this.canUserReact) return;

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
        formatDate(dateString) {
            if (!dateString) return '';
            const date = new Date(dateString);
            return date.toLocaleDateString('el-GR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
            });
        },
    },
    mounted() {
        // Initialize hasLiked and hasDisliked based on the backend data
        // Example:
        // this.hasLiked = videoClipWithComments.hasLiked;
        // this.hasDisliked = videoClipWithComments.hasDisliked;
    },
});

app.mount('#showVideoClip');
