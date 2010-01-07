package net.chilicat.m3u8;

/**
 * @author dkuffner
 */
class PlaylistFormat {
    private final Playlist pl;

    PlaylistFormat(Playlist pl) {
        if (pl == null) {
            throw new NullPointerException("pl");
        }
        this.pl = pl;
    }

    public String format() {
        StringBuffer buf = new StringBuffer(100);

        String NEW_LINE = "\n";
        String ELEMENT = "\n\t";

        buf.append("Playlist").append(NEW_LINE).append("Media Sequence No: ").append(pl.getMediaSequenceNumber())
                .append(" Target Duration: ").append(pl.getTargetDuration()).append(NEW_LINE);


        int index = 0;
        for (Element el : pl) {
            buf.append(ELEMENT).append(index).append(": ").append(" Dur: ").append(el.getDuration()).append(" URI: ").append(el.getURI()).append(" Title: ").append(el.getTitle());
        }
        return buf.toString();
    }
}
