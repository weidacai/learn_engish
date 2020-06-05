package com.example.learn.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.learn.model.WordModel;

import java.util.ArrayList;
import java.util.List;

public class AppDBHelp {
    private int dbVersion = 2;
    public DBHelp dbhelp;
    private static AppDBHelp appDBHelp;

    private AppDBHelp(Context context) {
        this.dbhelp = new DBHelp(context);
    }

    public synchronized static AppDBHelp getInstance(Context context) {
        if (appDBHelp == null)
            appDBHelp = new AppDBHelp(context);
        return appDBHelp;
    }

    /**Delete word library     */
    public void deleteWord(int id) {
        SQLiteDatabase db = null;
        try {
            db = dbhelp.getWritableDatabase();
            String sql = "DELETE FROM word WHERE id=" + id;
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("hel->", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**Add word library     */
    public void saveWord(WordModel wordModel) {
        SQLiteDatabase db = null;
        try {
            db = dbhelp.getWritableDatabase();
            String sql = "INSERT INTO word(english,chinesea,chineseb,chinesec,chinesed,answer) VALUES('" + wordModel.getEnglish() + "','" + wordModel.getChinesea() + "','" + wordModel.getChineseb() + "','" + wordModel.getChinesec() + "','" + wordModel.getChinesed() + "','" + wordModel.getAnswer() + "')";
            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("hel->", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**Edit word learning*/
    public void updateWord(WordModel wordModel) {
        SQLiteDatabase db = null;
        try {
            db = dbhelp.getWritableDatabase();
            db.execSQL("UPDATE word SET english='" + wordModel.getEnglish() + "',chinesea='" + wordModel.getChinesea() + "',chineseb='" + wordModel.getChineseb() + "',chinesec='" + wordModel.getChinesec() + "',chinesed='" + wordModel.getChinesed() + "',answer WHERE id = " + wordModel.getId());
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    /** Update word learning time, whether it is correct*/
    public void learnWord(int id, int iscorrect) {
        SQLiteDatabase db = null;
        try {
            db = dbhelp.getWritableDatabase();
            db.execSQL("UPDATE word SET learntime=strftime('%s','now') , iscorrect = " + iscorrect + " WHERE id = " + id);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**Fuzzy search*/
    public List<WordModel> findWordList(String keywords) {
        List<WordModel> wordModelList = new ArrayList<WordModel>();
        WordModel wordModel = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "SELECT id,english,chinesea,chineseb,chinesec,chinesed,answer FROM word WHERE english like '%" + keywords + "%'";
        try {
            db = dbhelp.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(0));
                wordModel.setEnglish(cursor.getString(1));
                wordModel.setChinesea(cursor.getString(2));
                wordModel.setChineseb(cursor.getString(3));
                wordModel.setChinesec(cursor.getString(4));
                wordModel.setChinesed(cursor.getString(5));
                wordModel.setAnswer(cursor.getString(6));
                wordModelList.add(wordModel);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return wordModelList;
    }

    /**Get all unlearned words*/
    public List<WordModel> getWordList() {
        List<WordModel> wordModelList = new ArrayList<WordModel>();
        WordModel wordModel = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "SELECT id,english,chinesea,chineseb,chinesec,chinesed,answer FROM word WHERE learntime IS NULL";
        try {
            db = dbhelp.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(0));
                wordModel.setEnglish(cursor.getString(1));
                wordModel.setChinesea(cursor.getString(2));
                wordModel.setChineseb(cursor.getString(3));
                wordModel.setChinesec(cursor.getString(4));
                wordModel.setChinesed(cursor.getString(5));
                wordModel.setAnswer(cursor.getString(6));
                wordModelList.add(wordModel);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return wordModelList;
    }

    /**Get words learned today*/
    public List<WordModel> getTodayWordList() {
        List<WordModel> wordModelList = new ArrayList<WordModel>();
        WordModel wordModel = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "SELECT id,english,chinesea,chineseb,chinesec,chinesed,answer FROM word WHERE learntime>=strftime('%s',date('now', 'localtime'))-8*60*60 AND learntime<strftime('%s',date('now', '+1 day', 'localtime'))-8*60*60";
        try {
            db = dbhelp.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(0));
                wordModel.setEnglish(cursor.getString(1));
                wordModel.setChinesea(cursor.getString(2));
                wordModel.setChineseb(cursor.getString(3));
                wordModel.setChinesec(cursor.getString(4));
                wordModel.setChinesed(cursor.getString(5));
                wordModel.setAnswer(cursor.getString(6));
                wordModelList.add(wordModel);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return wordModelList;
    }

    /**Get learned words*/
    public List<WordModel> getLearnedWordList() {
        List<WordModel> wordModelList = new ArrayList<WordModel>();
        WordModel wordModel = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select id,english,chinesea,chineseb,chinesec,chinesed,answer from word where learntime is not null";
        try {
            db = dbhelp.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(0));
                wordModel.setEnglish(cursor.getString(1));
                wordModel.setChinesea(cursor.getString(2));
                wordModel.setChineseb(cursor.getString(3));
                wordModel.setChinesec(cursor.getString(4));
                wordModel.setChinesed(cursor.getString(5));
                wordModel.setAnswer(cursor.getString(6));
                wordModelList.add(wordModel);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return wordModelList;
    }

    /** Get the wrong words*/
    public List<WordModel> getWrongWordList() {
        List<WordModel> wordModelList = new ArrayList<WordModel>();
        WordModel wordModel = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select id,english,chinesea,chineseb,chinesec,chinesed,answer from word where iscorrect = 0";
        try {
            db = dbhelp.getReadableDatabase();
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                wordModel = new WordModel();
                wordModel.setId(cursor.getInt(0));
                wordModel.setEnglish(cursor.getString(1));
                wordModel.setChinesea(cursor.getString(2));
                wordModel.setChineseb(cursor.getString(3));
                wordModel.setChinesec(cursor.getString(4));
                wordModel.setChinesed(cursor.getString(5));
                wordModel.setAnswer(cursor.getString(6));
                wordModelList.add(wordModel);
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return wordModelList;
    }

    public class DBHelp extends SQLiteOpenHelper {
        public void onCreate(SQLiteDatabase sqlitedatabase) {
            sqlitedatabase.execSQL("CREATE TABLE [word](\n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "  [english] NVARCHAR(250), \n" +
                    "  [chinesea] NVARCHAR(250), \n" +
                    "  [chineseb] NVARCHAR(250), \n" +
                    "  [chinesec] NVARCHAR(250), \n" +
                    "  [chinesed] NVARCHAR(250), \n" +
                    "  [answer] NVARCHAR(10), \n" +
                    "  [learntime] INT, \n" +
                    "  [iscorrect] INT)");
            sqlitedatabase.execSQL("INSERT INTO [word]([id], [english], [chinesea], [chineseb], [chinesec], [chinesed], [answer], [learntime], [iscorrect]) VALUES (1, 'routine', '常规; 正常顺序; 生活乏味; 无聊;', '溃败; 彻底失败;', '路线; 路途; 途径; 渠道;', '轨道; 路由;', 'A', null, null),(2, 'perturb', '使焦虑; 使不安;', '扰电; 测量仪;', '评估; 审查;', '有关的; 恰当的; 相宜的', 'A', null, null),(3, 'padding', '衬料; 衬垫; 填充;', '页边空白; 白边; ', '桨; 船桨; (机具的) 桨状部分;', '测试; 调试;', 'A', null, null),(4, 'speak', '陌生的; 奇怪的', '饮料', '说; 声明; 表现;', '受伤处;损害', 'C', null, null),(5, 'player', '在别处; 到别处;', '比赛者; 演奏者; ', '紧张; 压力;', '规则; 规章;', 'B', null, null),(6, 'daughter', '阴谋; 情节; 图;', '令人不快的; 侮辱的; ', '女儿;', '扩大; 扩充;', 'C', null, null),(7, 'argue', '运送者; 行李架; 客运公司;', '拉屎; ', '争论; 辩论; 劝说;', '艺术的;', 'C', null, null),(8, 'weapon', '武器; 兵器;', '青少年的; 十几岁的; ', '侵入; 侵略;', '歌剧;', 'A', null, null),(9, 'middle', '合作; 通敌;', '中间; 中部; ', '第九; 九分之一; ', '火箭; 烟火;', 'A', null, null),(10, 'concept', '被子; 被状物;', '慨念; 观念; ', '确认; 证实; 坚信;', '格子; 非实质的;', 'B', null, null);");
            Log.i("hel->", "===========================create sql=============================");
        }

        public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldV, int newV) {
            Log.i("hel->", "===========================drop sql=============================");
        }

        public DBHelp(Context context) {
            super(context, "word.db", null, dbVersion);
        }
    }

}