package com.hang.pulllog.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IDataWrite {
    void offerData(List<Map<String, String>> mapList, String serverId);

    void ultimateData() throws InterruptedException, IOException;
}
