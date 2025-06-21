// Author: KEJIYU
// 功能：输入关键词后长按发送按钮，自动获取并发送相应内容

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.view.*;
import android.widget.*;
import android.graphics.drawable.GradientDrawable;
import org.json.JSONObject;
import me.hd.wauxv.plugin.api.callback.PluginCallBack;
import java.io.File;
import java.net.URLEncoder;

// 新增：用于记录上一条媒体文件路径
String lastMediaFilePath = "";

void showHelpDialog() {
    Activity activity = getTopActivity();
    if (activity == null) return;
    
    activity.runOnUiThread(new Runnable() {
        public void run() {
            SpannableStringBuilder helpMessage = new SpannableStringBuilder();
            
            appendGoldText(helpMessage, "功能说明：\n\n");
            
            // 文字类功能
            appendGoldText(helpMessage, "【文字类】\n");
            appendGoldText(helpMessage, "1. 舔狗语录：输入「舔狗」\n");
            appendGoldText(helpMessage, "2. 问候语：输入「问候」\n");
            appendGoldText(helpMessage, "3. 童锦程经典语录：输入「名言」\n");
            appendGoldText(helpMessage, "4. 美句摘抄：输入「美句」\n");
            appendGoldText(helpMessage, "5. 随机美食：输入「吃什么」\n");
            appendGoldText(helpMessage, "6. KFC文案：输入「kfc」\n");
            appendGoldText(helpMessage, "7. 历史上的今天：输入「历史」\n");
            appendGoldText(helpMessage, "8. 世界人口：输入「世界人口」响应较慢\n");
            appendGoldText(helpMessage, "9. 天气查询：输入「天气+城市」如「天气北京」\n");
            appendGoldText(helpMessage, "10. 十宗罪语录：输入「十宗罪」\n");
            appendGoldText(helpMessage, "11. 土味情话：输入「土味情话」\n");
            appendGoldText(helpMessage, "12. 骚话：输入「骚话」\n\n"); // 新增
            
            // 图片类功能
            appendGoldText(helpMessage, "【图片类】\n");
            appendGoldText(helpMessage, "1. 每日晨报：输入「晨报」\n");
            appendGoldText(helpMessage, "2. 腹肌图片：输入「腹肌」\n");
            appendGoldText(helpMessage, "3. 诱惑图片：输入「诱惑」相应较慢\n");
            appendGoldText(helpMessage, "4. 黑丝图片：输入「黑丝」\n");
            appendGoldText(helpMessage, "5. 动漫图片：输入「动漫」\n");
            appendGoldText(helpMessage, "6. 买家秀：输入「买家秀」\n"); // 新增
            
            // 视频类功能
            appendGoldText(helpMessage, "【视频类】\n");
            appendGoldText(helpMessage, "1. 小姐姐视频：输入「小姐姐」\n");
            appendGoldText(helpMessage, "2. 女大视频：输入「女大」\n");
            appendGoldText(helpMessage, "3. COS视频：输入「cos」\n");
            appendGoldText(helpMessage, "4. 摸鱼日报：输入「摸鱼日报」响应较慢\n");
            appendGoldText(helpMessage, "5. 穿搭视频：输入「穿搭」\n");
            appendGoldText(helpMessage, "6. 玉足视频：输入「玉足」\n");
            appendGoldText(helpMessage, "7. 吊带视频：输入「吊带」\n");
            appendGoldText(helpMessage, "8. 女高视频：输入「女高」\n");
            appendGoldText(helpMessage, "9. 萝莉视频：输入「萝莉」\n");
            appendGoldText(helpMessage, "10. 变装视频：输入「变装」\n\n");
            
            // 其他功能
            appendGoldText(helpMessage, "【其他】\n");
            appendGoldText(helpMessage, "1. 赞助：输入「赞助」\n");
            appendGoldText(helpMessage, "2. 帮助：输入「帮助」\n\n");
            
            appendGoldText(helpMessage, "操作步骤：\n");
            appendGoldText(helpMessage, "1. 在聊天框输入触发词\n");
            appendGoldText(helpMessage, "2. 长按发送按钮（不要点击发送）\n");
            appendGoldText(helpMessage, "3. 系统会自动获取并发送内容");
            
            // 创建一个可滚动的视图
            ScrollView scrollableView = new ScrollView(activity);
            TextView messageView = new TextView(activity);
            messageView.setText(helpMessage);
            messageView.setTextSize(16);
            messageView.setPadding(40, 20, 40, 20);
            messageView.setGravity(Gravity.START);
            scrollableView.addView(messageView);
            
            AlertDialog dialog = new AlertDialog.Builder(activity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
            
            TextView customTitle = new TextView(activity);
            customTitle.setText("功能帮助");
            customTitle.setTextColor(Color.BLUE);
            customTitle.setTextSize(20);
            customTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            customTitle.setGravity(Gravity.CENTER);
            customTitle.setPadding(0, 20, 0, 20);
            dialog.setCustomTitle(customTitle);
            
            dialog.setView(scrollableView);
            
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            
            dialog.show();
            
            UIX(dialog, Color.TRANSPARENT, Color.BLUE, Color.parseColor("#4CAF50"));
        }
    });
}

void appendGoldText(SpannableStringBuilder builder, String text) {
    int start = builder.length();
    builder.append(text);
    int end = builder.length();
    builder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")),
                   start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
}

void UIX(Dialog dialog, int bgColor, int titleColor, int buttonColor) {
    Window dialogWindow = dialog.getWindow();
    if (dialogWindow != null) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(bgColor);
        background.setCornerRadius(16);
        
        dialogWindow.setBackgroundDrawable(background);
        
        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (button != null) {
            button.setTextColor(buttonColor);
            button.setTextSize(16);
        }
        
        if (dialog.getCustomTitle() != null) {
            TextView titleView = (TextView) dialog.getCustomTitle();
            titleView.setGravity(Gravity.CENTER);
        }
    }
}

void downloadAndSendImage(String talker, String type, String url) {
    String fileName = type + "_" + System.currentTimeMillis() + ".jpg";
    String filePath = pluginDir + "/" + fileName;
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(url, filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendImage(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "图片无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "图片获取失败，请重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：买家秀图片功能
void downloadAndSendBuyerShowImage(String talker) {
    String fileName = "买家秀_" + System.currentTimeMillis() + ".jpg";
    String filePath = pluginDir + "/" + fileName;
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download("http://api.yujn.cn/api/mjx.php?", filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendImage(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "买家秀图片无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "买家秀图片获取失败，请重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：土味情话功能
void getTwQingHua(String talker) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String apiUrl = "http://api.yujn.cn/api/qinghua.php?"; // 新增的API
                java.net.URL url = new java.net.URL(apiUrl);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                
                java.io.InputStream is = conn.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                sendText(talker, "\n" + response.toString());
            } catch (Exception e) {
                sendText(talker, "土味情话获取失败，请重试");
            }
        }
    }).start();
}

// 新增：小姐姐视频功能
void downloadAndSendXjjVideo(String talker) {
    String fileName = "video_xjj_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/xjj.php?type=video";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "小姐姐视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "小姐姐视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：黑丝图片功能
void downloadAndSendHeiSiImage(String talker) {
    String fileName = "heisi_" + System.currentTimeMillis() + ".jpg";
    String filePath = pluginDir + "/" + fileName;
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download("http://api.yujn.cn/api/heisi.php?", filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendImage(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "黑丝图片无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "黑丝图片获取失败，请重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：女大视频功能
void downloadAndSendNVDAVideo(String talker) {
    String fileName = "nvda_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "https://api.yujn.cn/api/nvda.php?type=video";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl, filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "女大视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "女大视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：动漫图片功能
void downloadAndSendACGImage(String talker) {
    String fileName = "acg_" + System.currentTimeMillis() + ".jpg";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "https://api.yujn.cn/api/gzl_ACG.php?type=image&amp;form=pc";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl, filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendImage(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "动漫图片无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "动漫图片获取失败，请重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：吊带视频功能
void downloadAndSendDiaoDaiVideo(String talker) {
    String fileName = "diaodai_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/diaodai.php?type=video";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "吊带视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "吊带视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：COS视频功能
void downloadAndSendCOSVideo(String talker) {
    String fileName = "cos_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/COS.php?type=video"; // 新的API地址
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "COS视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "COS视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：女高视频功能
void downloadAndSendNVGAOVideo(String talker) {
    String fileName = "nvgao_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/nvgao.php?type=video"; // 新的API地址
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "女高视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "女高视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：萝莉视频功能
void downloadAndSendLuoLiVideo(String talker) {
    String fileName = "luoli_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/luoli.php?type=video"; // 新的API地址
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "萝莉视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "萝莉视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 文字类：世界人口数据
void getWorldPopulation(String talker) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String apiUrl = "http://api.yujn.cn/api/sjrk.php?type=text";
                java.net.URL url = new java.net.URL(apiUrl);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                
                java.io.InputStream is = conn.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                sendText(talker, "世界人口数据：\n" + response.toString());
            } catch (Exception e) {
                sendText(talker, "世界人口数据获取失败");
            }
        }
    }).start();
}

// 文字类：天气查询
void getWeather(String talker, String city) {
    new Thread(new Runnable() {
        public void run() {
            try {
                // 对城市名进行URL编码
                String encodedCity = URLEncoder.encode(city, "UTF-8");
                String apiUrl = "http://api.yujn.cn/api/tianqi.php?msg=" + encodedCity + "&b=1";
                
                java.net.URL url = new java.net.URL(apiUrl);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                
                java.io.InputStream is = conn.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                sendText(talker, "天气信息：\n" + response.toString());
            } catch (Exception e) {
                sendText(talker, "天气查询失败，请检查城市名称");
            }
        }
    }).start();
}

// 新增：摸鱼日报美女视频功能
void downloadAndSendMoyuVideo(String talker) {
    String fileName = "moyu_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/moyu.php?msg=摸鱼日报美女视频&type=image";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "摸鱼日报美女视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "摸鱼日报美女视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：穿搭视频功能
void downloadAndSendChuandaVideo(String talker) {
    String fileName = "chuanda_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/chuanda.php?type=video";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "穿搭视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "穿搭视频下载失败，可能是网络问题或接口问题，请检查链接是否合法并稍后重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：玉足视频功能
void downloadAndSendYuZuVideo(String talker) {
    String fileName = "yuzu_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/jpmt.php?type=video";
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "玉足视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "玉足视频下载失败");
            lastMediaFilePath = "";
        }
    });
}

// 新增：变装视频功能
void downloadAndSendBianZhuangVideo(String talker) {
    String fileName = "bianzhuang_" + System.currentTimeMillis() + ".mp4";
    String filePath = pluginDir + "/" + fileName;
    
    String apiUrl = "http://api.yujn.cn/api/bianzhuang.php??"; // 新增的API
    
    // 检查并删除上一条媒体文件（如果存在）
    if (!lastMediaFilePath.isEmpty() && new File(lastMediaFilePath).exists()) {
        new File(lastMediaFilePath).delete();
    }
    
    lastMediaFilePath = filePath;
    
    download(apiUrl + "&t=" + System.currentTimeMillis(), filePath, null, new PluginCallBack.DownloadCallback() {
        public void onSuccess(File file) {
            if (file.exists() && file.length() > 1024) {
                sendVideo(talker, file.getAbsolutePath());
            } else {
                sendText(talker, "变装视频无效，请重试");
                if (file.exists()) file.delete();
                lastMediaFilePath = "";
            }
        }
        
        public void onError(Exception e) {
            sendText(talker, "变装视频下载失败，可能是网络问题或接口问题，请检查链接是否合法并稍后重试");
            lastMediaFilePath = "";
        }
    });
}

// 新增：十宗罪语录功能
void getSZZQuote(String talker) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String apiUrl = "http://api.yujn.cn/api/szz.php?"; // 新增的API
                java.net.URL url = new java.net.URL(apiUrl);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                
                java.io.InputStream is = conn.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                sendText(talker, "《十宗罪》语录：\n" + response.toString());
            } catch (Exception e) {
                sendText(talker, "十宗罪语录获取失败，请重试");
            }
        }
    }).start();
}

// 新增：骚话功能
void getSaoHua(String talker) {
    new Thread(new Runnable() {
        public void run() {
            try {
                String apiUrl = "https://v.api.aa1.cn/api/api-saohua/index.php?type=json"; // 新增的API
                java.net.URL url = new java.net.URL(apiUrl);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                
                java.io.InputStream is = conn.getInputStream();
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                // 解析 JSON 数据，只提取 saohua 字段的内容
                JSONObject jsonObject = new JSONObject(response.toString());
                String saohua = jsonObject.getString("saohua");
                
                sendText(talker, saohua);
            } catch (Exception e) {
                sendText(talker, "骚话获取失败，请重试");
            }
        }
    }).start();
}

boolean onLongClickSendBtn(String text) {
    String trimmedText = text.trim();
    String lowerText = trimmedText.toLowerCase();
    
    if ("帮助".equals(trimmedText)) {
        showHelpDialog();
        return true;
    }
    
    if ("赞助".equals(trimmedText)) {
        String talker = getTargetTalker();
        sendText(talker, "#付款:KEJIYU(KEJIYU)");
        return true;
    }
    
    // ======================= 文字类功能 =======================
    if ("舔狗".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/tgapi";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    sendText(talker, response.toString());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "舔狗语录获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("问候".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/getGreetingMessage";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    sendText(talker, response.toString());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "问候语获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("名言".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/zsyjdyl";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    sendText(talker, response.toString());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "童锦程语录获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("美句".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/bsnts";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    sendText(talker, response.toString());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "美句获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("吃什么".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/csm";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String foodName = jsonObject.getString("food");
                    
                    sendText(talker, "今日美食推荐：\n" + foodName);
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "美食推荐获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("kfc".equalsIgnoreCase(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/kfcv50";
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    
                    sendText(talker, response.toString());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "KFC文案获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    if ("历史".equals(trimmedText)) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    String talker = getTargetTalker();
                    String apiUrl = "https://api.ahfi.cn/api/lsjt";  
                    java.net.URL url = new java.net.URL(apiUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    
                    java.io.InputStream is = conn.getInputStream();
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    reader.close();
                    
                    sendText(talker, "历史上的今天：\n" + response.toString().trim());
                } catch (Exception e) {
                    String talker = getTargetTalker();
                    sendText(talker, "历史事件获取失败，请重试");
                }
            }
        }).start();
        return true;
    }
    
    // 文字类：世界人口查询
    if ("世界人口".equals(trimmedText)) {
        getWorldPopulation(getTargetTalker());
        return true;
    }
    
    // 文字类：天气查询
    if (trimmedText.startsWith("天气")) {
        String city = trimmedText.replace("天气", "").trim();
        if (city.isEmpty()) {
            sendText(getTargetTalker(), "请输入城市名称，例如：天气北京");
        } else {
            getWeather(getTargetTalker(), city);
        }
        return true;
    }
    
    // 新增：文字类功能，十宗罪语录
    if ("十宗罪".equals(trimmedText)) {
        getSZZQuote(getTargetTalker());
        return true;
    }
    
    // 新增：文字类功能，土味情话
    if ("土味情话".equals(trimmedText)) {
        getTwQingHua(getTargetTalker());
        return true;
    }
    
    // 新增：文字类功能，骚话
    if ("骚话".equals(trimmedText)) {
        getSaoHua(getTargetTalker());
        return true;
    }
    
    // ======================= 图片类功能 =======================
    if ("晨报".equals(trimmedText)) {
        downloadAndSendImage(getTargetTalker(), "晨报", "https://api.ahfi.cn/api/MorningNews");
        return true;
    }
    
    // 图片类：腹肌图片
    if ("腹肌".equals(trimmedText)) {
        downloadAndSendImage(getTargetTalker(), "腹肌", "http://api.yujn.cn/api/fujiimg.php?");
        return true;
    }
    
    // 图片类：诱惑图片
    if ("诱惑".equals(trimmedText)) {
        downloadAndSendImage(getTargetTalker(), "诱惑", "http://api.yujn.cn/api/yht.php");
        return true;
    }
    
    // 图片类：黑丝图片
    if ("黑丝".equals(trimmedText)) {
        downloadAndSendHeiSiImage(getTargetTalker());
        return true;
    }
    
    // 图片类：动漫图片
    if ("动漫".equals(trimmedText)) {
        downloadAndSendACGImage(getTargetTalker());
        return true;
    }
    
    // 图片类：买家秀图片
    if ("买家秀".equals(trimmedText)) {
        downloadAndSendBuyerShowImage(getTargetTalker());
        return true;
    }
    
    // ======================= 视频类功能 =======================
    // 新增：小姐姐视频功能
    if ("小姐姐".equals(trimmedText)) {
        downloadAndSendXjjVideo(getTargetTalker());
        return true;
    }
    
    // 新增：女大视频功能
    if ("女大".equals(trimmedText)) {
        downloadAndSendNVDAVideo(getTargetTalker());
        return true;
    }
    
    // 新增：COS视频功能
    if ("cos".equals(trimmedText)) {
        downloadAndSendCOSVideo(getTargetTalker());
        return true;
    }
    
    // 新增：摸鱼日报美女视频功能
    if ("摸鱼日报".equals(trimmedText)) {
        downloadAndSendMoyuVideo(getTargetTalker());
        return true;
    }
    
    // 新增：穿搭视频功能
    if ("穿搭".equals(trimmedText)) {
        downloadAndSendChuandaVideo(getTargetTalker());
        return true;
    }
    
    // 新增：玉足视频功能
    if ("玉足".equals(trimmedText)) {
        downloadAndSendYuZuVideo(getTargetTalker());
        return true;
    }
    
    // 新增：吊带视频功能
    if ("吊带".equals(trimmedText)) {
        downloadAndSendDiaoDaiVideo(getTargetTalker());
        return true;
    }
    
    // 新增：女高视频功能
    if ("女高".equals(trimmedText)) {
        downloadAndSendNVGAOVideo(getTargetTalker());
        return true;
    }
    
    // 新增：萝莉视频功能
    if ("萝莉".equals(trimmedText)) {
        downloadAndSendLuoLiVideo(getTargetTalker());
        return true;
    }
    
    // 新增：变装视频功能
    if ("变装".equals(trimmedText)) {
        downloadAndSendBianZhuangVideo(getTargetTalker());
        return true;
    }
    
    return false;
}

void onHandleMsg(Object msg) {
    if (!msg.isSend()) return;
    if (msg.isText()) {
        String c = msg.getContent().trim();
        String t = msg.getTalker();
        
        if ("赞助".equals(c)) {
            sendText(t, "#付款:KEJIYU(KEJIYU)");
        }
        
        // 图片类功能
        if ("诱惑".equals(c)) {
            downloadAndSendImage(t, "诱惑", "http://api.yujn.cn/api/yht.php");
        }
        
        // 图片类：黑丝图片
        if ("黑丝".equals(c)) {
            downloadAndSendHeiSiImage(t);
        }
        
        // 图片类：动漫图片
        if ("动漫".equals(c)) {
            downloadAndSendACGImage(t);
        }
        
        // 图片类：买家秀图片
        if ("买家秀".equals(c)) {
            downloadAndSendBuyerShowImage(t);
        }
        
        // 视频类功能
        if ("小姐姐".equals(c)) {
            downloadAndSendXjjVideo(t);
        }
        
        // 视频类：女大视频
        if ("女大".equals(c)) {
            downloadAndSendNVDAVideo(t);
        }
        
        // 视频类：COS视频
        if ("cos".equals(c)) {
            downloadAndSendCOSVideo(t);
        }
        
        // 视频类：摸鱼日报美女视频
        if ("摸鱼日报".equals(c)) {
            downloadAndSendMoyuVideo(t);
        }
        
        // 视频类：穿搭视频
        if ("穿搭".equals(c)) {
            downloadAndSendChuandaVideo(t);
        }
        
        // 视频类：玉足视频
        if ("玉足".equals(c)) {
            downloadAndSendYuZuVideo(t);
        }
        
        // 视频类：吊带视频
        if ("吊带".equals(c)) {
            downloadAndSendDiaoDaiVideo(t);
        }
        
        // 视频类：女高视频
        if ("女高".equals(c)) {
            downloadAndSendNVGAOVideo(t);
        }
        
        // 视频类：萝莉视频
        if ("萝莉".equals(c)) {
            downloadAndSendLuoLiVideo(t);
        }
        
        // 视频类：变装视频
        if ("变装".equals(c)) {
            downloadAndSendBianZhuangVideo(t);
        }
        
        // 新增：文字类功能，十宗罪语录
        if ("十宗罪".equals(c)) {
            getSZZQuote(t);
        }
        
        // 新增：文字类功能，土味情话
        if ("土味情话".equals(c)) {
            getTwQingHua(t);
        }
        
        // 新增：文字类功能，骚话
        if ("骚话".equals(c)) {
            getSaoHua(t);
        }
    }
}
