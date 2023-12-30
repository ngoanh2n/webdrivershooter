var width = Math.max(
    0,
    window.innerWidth - document.body.clientWidth,
    document.body.offsetWidth - document.body.clientWidth,
);

if (width == 0) {
    const docH = Math.max(
        document.body.scrollHeight,
        document.body.offsetHeight,
        document.documentElement.clientHeight,
        document.documentElement.scrollHeight,
        document.documentElement.offsetHeight
    );
    const viewportH = window.innerHeight ||
        document.documentElement.clientHeight ||
        document.body.clientHeight;
    const hasScroll = document.body.scrollHeight > document.body.clientHeight;

    if (hasScroll || (docH > viewportH)) {
        const outer = document.createElement('div');
        outer.style.visibility = 'hidden';
        outer.style.overflow = 'scroll';
        outer.style.msOverflowStyle = 'scrollbar';

        const inner = document.createElement('div');
        outer.appendChild(inner);
        document.body.appendChild(outer);

        width = (outer.offsetWidth - inner.offsetWidth);
        outer.parentNode.removeChild(outer);
    }
}

return Math.max(0, width);
