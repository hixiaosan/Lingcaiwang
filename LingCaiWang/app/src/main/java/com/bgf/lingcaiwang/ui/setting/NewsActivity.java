package com.bgf.lingcaiwang.ui.setting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bgf.lingcaiwang.R;
import com.bgf.lingcaiwang.bean.LcwResult;
import com.bgf.lingcaiwang.bean.News;
import com.bgf.lingcaiwang.data.SettingServiceCenter;
import com.bgf.lingcaiwang.ui.BackActivity;
import com.bgf.lingcaiwang.utils.Logger;
import com.demievil.library.RefreshLayout;
import com.hualong.framework.widget.LibToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 设置新闻列表
 *
 * @author Darren
 */
public class NewsActivity extends BackActivity {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private final String TAG = this.getClass().getSimpleName();

    //Data
    private List<News> newsList = null;

    //Adapter
    private NewsAdapter adapter;

    private boolean reset = true;

    private GetNewsList task = null;

    //Views
    private RefreshLayout swipeNews;
    private TextView more;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        setTitle(R.string.title_news);
        initViews();
    }

    private void initViews() {

        swipeNews = (RefreshLayout) findViewById(R.id.swipe_news);
        ListView listNews = (ListView) findViewById(R.id.list_news);

        View footerView = LayoutInflater.from(this).inflate(R.layout.load_more_layout, null);
        more = (TextView) footerView.findViewById(R.id.text_more);
        load = (ProgressBar) footerView.findViewById(R.id.load_progress_bar);

        adapter = new NewsAdapter(null);
        listNews.addFooterView(footerView);
        listNews.setAdapter(adapter);

        swipeNews.setChildView(listNews);

        startQuery(getSmallId(null));

        swipeNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reset = true;
                startQuery(getSmallId(null));
            }
        });
        swipeNews.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                more.setVisibility(View.GONE);
                load.setVisibility(View.VISIBLE);
                reset = false;
                startQuery(getSmallId(newsList));
            }
        });
    }

    public String getSmallId(List<News> newsList) {
        if (newsList == null) {
            return "";
        } else {
            if (newsList.size() == 0) {
                return "";
            } else {
                return newsList.get(newsList.size() - 1).getId();
            }
        }
    }

    public void stopQuery() {
        if (task != null) {
            task.cancel(true);
        }
    }

    public void startQuery(String listId) {
        stopQuery();
        task = new GetNewsList(listId);
        task.execute();
    }

    class GetNewsList extends AsyncTask<Void, Void, LcwResult<List<News>>> {

        private String listId;

        public GetNewsList(String listId) {
            this.listId = listId;
        }

        @Override
        protected LcwResult<List<News>> doInBackground(Void... params) {
            try {
                return SettingServiceCenter.getNews(listId);
            } catch (Exception e) {
                Logger.d(TAG, e.toString());
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected void onPostExecute(LcwResult<List<News>> listLcwResult) {
            super.onPostExecute(listLcwResult);
            hideProgress();
            swipeNews.setRefreshing(false);
            swipeNews.setLoading(false);
            more.setVisibility(View.VISIBLE);
            load.setVisibility(View.GONE);
            if (listLcwResult != null) {
                if (listLcwResult.isSuccess()) {
                    if (reset) {
                        newsList = listLcwResult.getData();
                    } else {
                        if (newsList != null) {
                            newsList.addAll(listLcwResult.getData());
                        } else {
                            newsList = listLcwResult.getData();
                        }
                    }
                    adapter.setNewsList(newsList);
                    adapter.notifyDataSetChanged();
                } else {
                    LibToast.show(listLcwResult.getMsg());
                }
            } else {
                LibToast.show(R.string.network_error);
            }
        }
    }

    class NewsAdapter extends BaseAdapter {

        private List<News> newsList;

        public NewsAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        public void setNewsList(List<News> newsList) {
            this.newsList = newsList;
        }

        @Override
        public int getCount() {
            return newsList == null ? 0 : newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return newsList == null ? null : newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
                vh.news = (TextView) convertView.findViewById(R.id.text_news);
                vh.date = (TextView) convertView.findViewById(R.id.text_date);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            News item = (News) getItem(position);

            if (item != null) {
                vh.news.setText(item.getTitle());
                try {
                    long time = Long.parseLong(item.getArt_time());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(time);
                    vh.date.setText(df.format(calendar.getTime()));
                } catch (Exception e) {
                    Logger.d(TAG, e.toString());
                }
            }

            return convertView;
        }

        class ViewHolder {
            TextView news;
            TextView date;
        }
    }
}
