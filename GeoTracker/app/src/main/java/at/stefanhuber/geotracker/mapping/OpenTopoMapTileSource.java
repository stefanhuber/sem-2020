package at.stefanhuber.geotracker.mapping;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.MapTileIndex;

public class OpenTopoMapTileSource extends OnlineTileSourceBase {

    public OpenTopoMapTileSource() {
        super("opentopomap.org", 1, 17, 256, ".png", new String[] {
            "https://a.tile.opentopomap.org/"  ,
            "https://b.tile.opentopomap.org/" ,
            "https://c.tile.opentopomap.org/"
        });
    }

    @Override
    public String getTileURLString(long pMapTileIndex) {
        return getBaseUrl()
                + MapTileIndex.getZoom(pMapTileIndex)
                + "/" + MapTileIndex.getX(pMapTileIndex)
                + "/" + MapTileIndex.getY(pMapTileIndex)
                + mImageFilenameEnding;
    }
}
