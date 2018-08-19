package com.orange.lightning;

import java.io.IOException;

public interface Extraction {
    String parentUrl();

    DdObjects extractChildPages() throws IOException;

    DdObject extractFromPage(String url, int id) throws IOException;
}
