var height = height = Math.max(
    0,
    window.innerHeight - document.body.clientHeight,
    document.body.offsetHeight - document.body.clientHeight,
);

if (height == 0) {
    const docW = Math.max(
        document.body.scrollWidth,
        document.body.offsetWidth,
        document.documentElement.clientWidth,
        document.documentElement.scrollWidth,
        document.documentElement.offsetWidth
    );
    const viewportW = window.innerWidth ||
        document.documentElement.clientWidth ||
        document.body.clientWidth;
    const hasScroll = document.body.scrollWidth > document.body.clientWidth;

    if (hasScroll || (docW > viewportW)) {
        const outer = document.createElement('div');
        outer.style.visibility = 'hidden';
        outer.style.overflow = 'scroll';
        outer.style.msOverflowStyle = 'scrollbar';

        const inner = document.createElement('div');
        outer.appendChild(inner);
        document.body.appendChild(outer);

        height = (outer.offsetHeight - inner.offsetHeight);
        outer.parentNode.removeChild(outer);
    }
}

return Math.max(0, height);
