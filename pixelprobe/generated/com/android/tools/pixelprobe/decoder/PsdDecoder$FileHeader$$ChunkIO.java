package com.android.tools.pixelprobe.decoder;

import com.android.tools.chunkio.ChunkUtils;
import com.android.tools.chunkio.RangedInputStream;
import com.android.tools.pixelprobe.ColorMode;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;

final class PsdDecoder$FileHeader$$ChunkIO {
    static PsdDecoder.FileHeader read(RangedInputStream in, LinkedList<Object> stack) throws IOException {
        PsdDecoder.FileHeader fileHeader = new PsdDecoder.FileHeader();
        stack.addFirst(fileHeader);

        int size = 0;
        long byteCount = 0;

        fileHeader.signature = ChunkUtils.readString(in, 4, Charset.forName("ISO-8859-1"));
        ChunkUtils.checkState(fileHeader.signature.equals("8BPS"),
                "Value read in signature does not match expected value");
        fileHeader.version = in.readShort();
        ChunkUtils.checkState(fileHeader.version == (1),
                "Value read in version does not match expected value");
        /* fileHeader.reserved */
        ChunkUtils.skip(in, 6);
        fileHeader.channels = in.readUnsignedShort();
        fileHeader.height = in.readInt();
        fileHeader.width = in.readInt();
        fileHeader.depth = in.readShort();
        fileHeader.colorMode = ColorMode.values()[
                Math.max(0, Math.min(in.readUnsignedShort(), ColorMode.values().length - 1))];

        stack.removeFirst();
        return fileHeader;
    }
}
