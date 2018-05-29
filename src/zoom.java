class zoom {
    private int x = 5000;
    private int y = 5000;
    private double zoomNumb = 1;

    zoom(int x, int y, double zoomNumb) {
        this.x = x;
        this.y = y;
        this.zoomNumb = zoomNumb;
    }

    double getZoomNumb() {
        return zoomNumb;
    }
}
