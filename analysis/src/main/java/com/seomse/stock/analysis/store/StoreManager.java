/*
 * Copyright (C) 2020 Seomse Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seomse.stock.analysis.store;

import com.seomse.stock.analysis.store.etf.EtfStore;
import com.seomse.stock.analysis.store.index.MarketIndexStore;
import com.seomse.stock.analysis.store.item.ItemStore;
import com.seomse.stock.analysis.store.market.domestic.DomesticMarketStore;
import com.seomse.stock.analysis.store.market.overseas.OverseasMarketStore;
import com.seomse.stock.analysis.store.preferred.PreferredStore;
import com.seomse.stock.analysis.store.wics.WicsStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 저장소 관리자
 * @author macle
 */
public class StoreManager {


    //개별종목
    private final Map<String, ItemStore> itemStoreMap = new HashMap<>();

    private final Map<String, Object> itemYmdLockMap = new HashMap<>();
    private final Object itemLock = new Object();

    /**
     * 개별종목 저장소 얻기
     * @param ymd yyyyMMdd
     * @return 개별종목 저장소
     */
    public ItemStore getItemStore(String ymd){
        ItemStore itemStore = itemStoreMap.get(ymd);
        if(itemStore == null){

            Object ymdLock;
            synchronized (itemLock){
                ymdLock = itemYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }

            synchronized (ymdLock){
                //lock 구간에서 다시 체크
                itemStore = itemStoreMap.get(ymd);
                if(itemStore == null){
                    itemStore = new ItemStore(ymd);
                    itemStoreMap.put(ymd, itemStore);
                }
            }
        }
        return itemStore;
    }
    

    //우선주
    private final Map<String, PreferredStore> preferredStoreMap = new HashMap<>();
    private final Map<String, Object> preferredYmdLockMap = new HashMap<>();
    private final Object preferredLock = new Object();

    /**
     * 우선주 저장소 얻기
     * @param ymd yyyyMMdd
     * @return 우선주 저장소
     */
    public PreferredStore getPreferredStore(String ymd){
        ItemStore itemStore = getItemStore(ymd);
        PreferredStore preferredStore = preferredStoreMap.get(ymd);
        if(preferredStore== null){

            Object ymdLock;
            synchronized (preferredLock){
                ymdLock = preferredYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }

            //lock 객체를 년원일 별로 관리하기
            synchronized (ymdLock){
                preferredStore = preferredStoreMap.get(ymd);
                if(preferredStore == null){
                    preferredStore = new PreferredStore(itemStore);
                    preferredStoreMap.put(ymd, preferredStore);
                }
            }
        }
        return preferredStore;
    }

    
    //etf
    private final Map<String, EtfStore> etfStoreMap = new HashMap<>();
    private final Map<String, Object> etfYmdLockMap = new HashMap<>();

    private final Object etfLock = new Object();

    /**
     * etf 저장소 얻기
     * @param ymd yyyyMMdd
     * @return etf 저장소
     */
    public EtfStore getEtfStore(String ymd){
        EtfStore etfStore= etfStoreMap.get(ymd);
       
        if(etfStore == null){
            Object ymdLock;
            synchronized (etfLock){
                ymdLock = etfYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }


            synchronized (ymdLock){
                etfStore= etfStoreMap.get(ymd);
                if(etfStore == null){
                    etfStore =new EtfStore(ymd);
                    etfStoreMap.put(ymd, etfStore);
                }
            }
        }
        return etfStore;
    }
    
    //국내증시
    private final Map<String, DomesticMarketStore> domesticMarketStoreMap = new HashMap<>();
    private final Map<String, Object> domesticMarketYmdLockMap = new HashMap<>();
    private final Object domesticMarketLock = new Object();

    /**
     * 국내 증시 얻기
     * @param ymd yyyyMMdd
     * @return 국내증시
     */
    public DomesticMarketStore getDomesticMarketStore(String ymd){
        DomesticMarketStore domesticMarketStore = domesticMarketStoreMap.get(ymd);
        
        if(domesticMarketStore == null){

            Object ymdLock;
            synchronized (domesticMarketLock){
                ymdLock = domesticMarketYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }

            synchronized (ymdLock){
                domesticMarketStore = domesticMarketStoreMap.get(ymd);
                if(domesticMarketStore == null){
                    domesticMarketStore = new DomesticMarketStore(ymd);
                    domesticMarketStoreMap.put(ymd, domesticMarketStore);
                }
            }
        }
        
        return domesticMarketStore;
    }
    

    //해외증시
    private final Map<String, OverseasMarketStore> overseasMarketStoreMap = new HashMap<>();
    private final Map<String, Object> overseasMarketYmdLockMap = new HashMap<>();
    private final Object overseasMarketLock = new Object();
    /**
     * 해외증시 얻기
     * @param ymd yyyyMMdd
     * @return 해외증시
     */
    public OverseasMarketStore getOverseasMarketStore(String ymd){
        OverseasMarketStore overseasMarketStore =  overseasMarketStoreMap.get(ymd);

        if(overseasMarketStore == null){


            Object ymdLock;
            synchronized (overseasMarketLock){
                ymdLock = overseasMarketYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }


            synchronized (ymdLock){
                overseasMarketStore = overseasMarketStoreMap.get(ymd);
                if(overseasMarketStore == null){
                    overseasMarketStore = new OverseasMarketStore(ymd);
                    overseasMarketStoreMap.put(ymd, overseasMarketStore);
                }
            }
        }
        return overseasMarketStore;
    }

    
    //시장지수
    private final Map<String, MarketIndexStore> indexStoreMap = new HashMap<>();
    private final Map<String, Object> indexYmdLockMap = new HashMap<>();
    private final Object indexStoreLock = new Object();

    /**
     * 시장지수 얻기
     * @param ymd yyyyMMdd
     * @return 시장지수
     */
    public MarketIndexStore getMarketIndexStore(String ymd){
        MarketIndexStore marketIndexStore = indexStoreMap.get(ymd);
        if(marketIndexStore == null){

            Object ymdLock;
            synchronized (indexStoreLock){
                ymdLock = indexYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }
            synchronized (ymdLock){
                marketIndexStore = indexStoreMap.get(ymd);
                if(marketIndexStore == null){
                    marketIndexStore = new MarketIndexStore(ymd);
                    indexStoreMap.put(ymd, marketIndexStore);
                    
                }
                
            }
        }
        
        return marketIndexStore;
    }

    private final Map<String, WicsStore> wicsStoreMap = new HashMap<>();
    private final Map<String, Object> wicsYmdLockMap = new HashMap<>();
    private final Object wicsStoreLock = new Object();

    /**
     * wics 통계 저장소 얻기
     * @param ymd yyyyMMdd
     * @return wics 통계 저장소
     */
    public WicsStore getWicsStore(String ymd){
        WicsStore wicsStore = wicsStoreMap.get(ymd);
        if(wicsStore == null){

            Object ymdLock;
            synchronized (wicsStoreLock){
                ymdLock = wicsYmdLockMap.computeIfAbsent(ymd, k -> new Object());
            }

            synchronized (ymdLock){
                wicsStore = wicsStoreMap.get(ymd);
                if(wicsStore == null){
                    wicsStore = new WicsStore(ymd);
                    wicsStoreMap.put(ymd, wicsStore);
                }
            }
        }
        return wicsStore;
    }

    // 테마는 삭제하지 않음
    // 아직 많지 않은 정보를 사용하는 것.
    // 과거 테마정보는 없어서 정보의 오차가 있을 수 있음

    /**
     * 일자에 해당하는 store 제거( 메모리 관리)
     * @param ymd  yyyyMMdd
     */
    public void remove(String ymd){
        synchronized (itemLock){
            itemStoreMap.remove(ymd);
            itemYmdLockMap.remove(ymd);
        }
        synchronized (preferredLock){
            preferredStoreMap.remove(ymd);
            preferredYmdLockMap.remove(ymd);
        }
        synchronized (etfLock){
            etfStoreMap.remove(ymd);
            etfYmdLockMap.remove(ymd);
        }
        synchronized (domesticMarketLock){
            domesticMarketStoreMap.remove(ymd);
            domesticMarketYmdLockMap.remove(ymd);
        }
        synchronized (overseasMarketLock){
            overseasMarketStoreMap.remove(ymd);
            overseasMarketYmdLockMap.remove(ymd);
        }
        synchronized (indexStoreLock){
            indexStoreMap.remove(ymd);
            indexYmdLockMap.remove(ymd);
        }
        synchronized (wicsStoreLock){
            wicsStoreMap.remove(ymd);
            wicsYmdLockMap.remove(ymd);
        }
    }


}