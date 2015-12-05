package com.bgf.lingcaiwang.data;

import com.bgf.lingcaiwang.bean.LcwResult;
import com.bgf.lingcaiwang.bean.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hualong.framework.net.ResponseHelper;

import org.apache.http.HttpResponse;

import java.util.List;

/**
 * 设置数据中心
 */
public class SettingServiceCenter {

    /**
     * 查询新闻列表
     *
     * @param list_id 开始的list_id
     * @author Darren
     */
    public static LcwResult<List<News>> getNews(String list_id) {
        LcwResult<List<News>> result = null;
        String uri = String.format("Article/index/typeid/4/page/%s", list_id);
        HttpResponse response = ServiceCenter.get(uri);
        if (isResponseSuccess(response)) {
            Gson gson = new Gson();
            String resp = ResponseHelper.parseString(response);
            result = gson.fromJson(resp, new TypeToken<LcwResult<List<News>>>() {
            }.getType());
        }
        return result;
    }

    private static boolean isResponseSuccess(HttpResponse response) {
        return (response != null && response.getStatusLine().getStatusCode() == 200);

    }
}
