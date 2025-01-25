const app = Vue.createApp({
    data() {
        return {
            videoClips: videoClips || [],
            currentVideoIndex: 0,
        };
    },
    computed: {
        hasVideos() {
            return this.videoClips.length > 0;
        },
        currentVideo() {
            return this.videoClips[this.currentVideoIndex] || null;
        },
    },
    methods: {
        syncWithCarousel(event) {
            const activeElement = event.relatedTarget;
            const index = Array.from(activeElement.parentNode.children).indexOf(activeElement);
            this.currentVideoIndex = index;
        },
        goToVideo(movieTitle) {
            const encodedTitle = encodeURIComponent(movieTitle); // Handle special characters
            window.location.href = `/showVideoClip/${encodedTitle}`;
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
        const carouselElement = document.getElementById('videoClipCarousel');
        carouselElement.addEventListener('slid.bs.carousel', this.syncWithCarousel);
    },
});

app.mount('#index');
