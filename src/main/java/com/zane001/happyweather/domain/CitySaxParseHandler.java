package com.zane001.happyweather.domain;

import com.zane001.happyweather.model.city.AreaModel;
import com.zane001.happyweather.model.city.CityModel;
import com.zane001.happyweather.model.city.ProvinceModel;
import com.zane001.happyweather.util.LogUtil;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zane001 on 2014/9/7.
 * 解析assets文件夹下的city.xml
 */
public class CitySaxParseHandler extends BaseSaxParseHandler {
    private ArrayList<ProvinceModel> mProvinceModels;
    private ArrayList<CityModel> mCityModels;
    private ArrayList<AreaModel> mAreaModels;
    private ProvinceModel mProvinceModel;
    private CityModel mCityModel;
    private AreaModel mAreaModel;
    private String tagName;

    public static ArrayList<ProvinceModel> getProvinceModel(InputStream in) throws Exception {
        // 得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 创建解析器
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        // 得到输入流
        InputSource is = new InputSource(in);
        // 得到SAX解析实现类
        CitySaxParseHandler handler = new CitySaxParseHandler();
        xmlreader.setContentHandler(handler);
        // 开始解析
        xmlreader.parse(is);
        return handler.mProvinceModels;
    }

    public static ArrayList<CityModel> getCityModel(InputStream in) throws Exception {
        // 得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 创建解析器
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        // 得到输入流
        InputSource is = new InputSource(in);
        // 得到SAX解析实现类
        CitySaxParseHandler handler = new CitySaxParseHandler();
        xmlreader.setContentHandler(handler);
        // 开始解析
        xmlreader.parse(is);
        return handler.mCityModels;
    }

    public static ArrayList<AreaModel> getAreaModel(InputStream in) throws Exception {
        // 得到SAX解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // 创建解析器
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        // 得到输入流
        InputSource is = new InputSource(in);
        // 得到SAX解析实现类
        CitySaxParseHandler handler = new CitySaxParseHandler();
        xmlreader.setContentHandler(handler);
        // 开始解析
        xmlreader.parse(is);
        return handler.mAreaModels;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (tagName != null) {

        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        LogUtil.i(TAG, "start parse xml");
        mProvinceModels = new ArrayList<ProvinceModel>();
        mCityModels = new ArrayList<CityModel>();
        mAreaModels = new ArrayList<AreaModel>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
//        LogUtil.i(TAG, "start read tag - " + localName);
        this.tagName = localName;
        if (tagName.equalsIgnoreCase("province")) {
            //省
            mProvinceModel = new ProvinceModel();
            mProvinceModel.setCityName(attributes.getValue("name"));
            mProvinceModel.setCityId(attributes.getValue("id"));
        } else if (tagName.equalsIgnoreCase("city")) {
            //市
            mCityModel = new CityModel();
            mCityModel.setCityName(attributes.getValue("name"));
            mCityModel.setCityId(attributes.getValue("id"));
        } else if (tagName.equalsIgnoreCase("county")) {
            //区/县
            mAreaModel = new AreaModel();
            mAreaModel.setCityName(attributes.getValue("name"));
            mAreaModel.setCityId(attributes.getValue("id"));
            mAreaModel.setWeatherCode(attributes.getValue("weatherCode"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
//        LogUtil.i(TAG, "end element - " + localName);
        this.tagName = null;
        if (localName.equals("province")) {
            //省
            mProvinceModels.add(mProvinceModel);
            mProvinceModel = null;
        } else if (localName.equals("city")) {
            //市
            mCityModels.add(mCityModel);
            mProvinceModel.getCityModels().add(mCityModel);
            mCityModel = null;
        } else if (localName.equals("county")) {
            //区/县
            mAreaModels.add(mAreaModel);
            mCityModel.getAreaModels().add(mAreaModel);
            mAreaModel = null;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        LogUtil.i(TAG, "end parse xml");
    }
}
