package com.shop.core.wms.info.dao;

import com.shop.core.biz.common.vo.request.PageRequest;
import com.shop.core.biz.common.vo.response.PageResponse;
import com.shop.core.entity.LbProd;
import com.shop.core.entity.LbSelling;
import com.shop.core.entity.LbVersionSeller;
import com.shop.core.wms.info.vo.request.LbProdRequest;
import com.shop.core.wms.info.vo.request.ipgologRequest;
import com.shop.core.wms.info.vo.response.LbProdResponse;
import com.shop.core.wms.info.vo.response.ipgologResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 적치정보 DAO
 */

@Repository
@RequiredArgsConstructor
public class LbProdDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.shop.mapper.wms.info.LbProdMapper.";

    /**
     * 적치 목록을 페이징하여 조회
     * @param pageRequest 필터값
     * @return 적치목록
     */
    public PageResponse<LbProdResponse.Paging> selectLbProdList(PageRequest<LbProdRequest.PagingFilter> pageRequest) {
        List<LbProdResponse.Paging> lbProdList = sqlSession.selectList(NAMESPACE.concat("selectLbProdList"), pageRequest);
        if (ObjectUtils.isNotEmpty(lbProdList)) {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount(), lbProdList, lbProdList.size());
        } else {
            return new PageResponse<>(pageRequest.getCurPage(), pageRequest.getPageRowCount());
        }
    }



    /**
     * 적치 목록을 페이징하여 조회
     * @param download 필터값
     * @return 적치목록
     */
    public List<LbProdResponse.LbProdList>  selectLbProdListAll(LbProdRequest.LiveExcelDownload download) {
        return sqlSession.selectList(NAMESPACE.concat("selectLbProdListAll"), download);
    }


    /**
     * 적치 목록을 페이징하여 조회
     * @param download 필터값
     * @return 적치목록
     */
    public List<LbProdResponse.LbProdListForLabel>  selectLbProdListForMakeLabel(LbProdRequest.LiveExcelDownload download) {
        return sqlSession.selectList(NAMESPACE.concat("selectLbProdListForMakeLabel"), download);
    }

    /**
     * ID로 상품 정보 조회
     *
     * @param id 상품 ID
     * @return 상품 정보
     */
    public LbProd selectLbProdById(int id) {
        return sqlSession.selectOne(NAMESPACE.concat("selectLbProdById"), id);
    }

    /**
     * ID로 상품 정보 삭제
     *
     * @param updUser 상품 ID
     * @return 상품 정보
     */
    public void deleteLbSelling(String lbProdId, String updUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("lbProdId", lbProdId);
        params.put("updUser", updUser);
        sqlSession.update(NAMESPACE.concat("deleteLbSelling"), params);
    }

    /**
     * lbVersion  skuNm 상품 정보 조회
     * @param skuNm 상품
     * @return 상품 정보
     */
    public LbProd selectLbProdBySkuNm(String lbVersion , String skuNm) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbVersion", lbVersion);
        paramMap.put("skuNm", skuNm);
        LbProd lbProd = sqlSession.selectOne(NAMESPACE.concat("selectLbProdBySkuNm"), paramMap);

        if(lbProd != null) {
            System.out.println(lbProd.getSkuCnt());
        }
        return lbProd;
    }

    /**
     * lbVersion  skuNm 상품 정보 조회
     * @param skuNm 상품
     * @return 상품 정보
     */
    public LbProd selectLbProdSkuCntBySkuNm(String lbVersion , String skuNm) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbVersion", lbVersion);
        paramMap.put("skuNm", skuNm);
        LbProd lbProd = sqlSession.selectOne(NAMESPACE.concat("selectLbProdSkuCntBySkuNm"), paramMap);
        if(lbProd != null) {
            System.out.println(lbProd.getSkuCnt());
        }
        return lbProd;
    }


    /**
     * lbVersion  skuNm 상품 정보 조회
     * @param prodNm 상품
     * @return 상품 정보
     */
    public LbProd selectLbProdByProdNmColorSize(String lbVersion , String prodNm, String color, String size) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbVersion", lbVersion);
        paramMap.put("prodNm", prodNm);
        paramMap.put("color", color);
        paramMap.put("size", size);
        return sqlSession.selectOne(NAMESPACE.concat("selectLbProdByProdNmColorSize"), paramMap);
    }

    /**
     * lbVersion  skuNm 상품 정보 조회
     * @param sellerId 상품
     * @return 상품 정보
     */
    public Integer selectLbSellingByUk(Integer lbProdId , Integer sellerId, String lbVersion) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbProdId", lbProdId);
        paramMap.put("sellerId", sellerId);
        paramMap.put("lbVersion", lbVersion);
        return sqlSession.selectOne(NAMESPACE.concat("selectLbSellingByUk"), paramMap);
    }

    /**
     * 상품 정보 등록
     *
     * @param lbProd 상품 엔티티
     * @return 등록된 건 수
     */
    public void insertLbProd(LbProd lbProd) {
        sqlSession.insert(NAMESPACE.concat("insertLbProd"), lbProd);
    }

    /**
     * 상품 정보 수정 (null이 아닌 항목만 업데이트)
     *
     * @param lbProd 수정할 상품 엔티티
     * @return 수정된 건 수
     */
     public void updateLbProd(LbProd lbProd){
         sqlSession.update(NAMESPACE.concat("updateLbProd"), lbProd);
     }

    /**
     * 상품 정보 수정 (null이 아닌 항목만 업데이트)
     *
     * @param lbVersion 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public int updateLbProdInitInven(String lbVersion){
        return sqlSession.update(NAMESPACE.concat("updateLbProdInitInven"), lbVersion);
    }


    /**
     * 상품 정보 등록
     *
     * @param lbSelling 상품 엔티티
     * @return 등록된 건 수
     */
    public void insertLbSelling(LbSelling lbSelling) {
        sqlSession.insert(NAMESPACE.concat("insertLbSelling"), lbSelling);
    }

    /**
     * 상품 정보 수정 (null이 아닌 항목만 업데이트)
     *
     * @param lbSelling 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void updateLbSelling(LbSelling lbSelling){
        sqlSession.update(NAMESPACE.concat("updateLbSelling"), lbSelling);
    }

    /**
     * 상품 정보 수정 (null이 아닌 항목만 업데이트)
     *
     * @param lbSelling 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void updateLbSellingEtc(LbSelling lbSelling){
        sqlSession.update(NAMESPACE.concat("updateLbSellingEtc"), lbSelling);
    }


    /**
     *
     * @param lbSelling 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void deleteAllLbSelling(LbSelling lbSelling){
        sqlSession.update(NAMESPACE.concat("deleteAllLbSelling"), lbSelling);
    }



    /**
     * 버전별 셀러보회
     *
     * @param lbVersion 조회할 버전
     * @return 셀러목록
     */
    public List<LbProdResponse.LbVersionSellerMapList> selectLbVersionSellerList(String lbVersion){
        return sqlSession.selectList(NAMESPACE.concat("selectLbVersionSellerList"), lbVersion);
    }


    /**
     * 버전별 셀러보회
     *
     * @param lbVersion 조회할 버전
     * @return 셀러목록
     */
    public List<LbProdResponse.LbVersionSellerMapList> selectLbVersionSellerListAll(String lbVersion){
        return sqlSession.selectList(NAMESPACE.concat("selectLbVersionSellerListAll"), lbVersion);
    }

    /**
     *
     * @param lbVersionSeller 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void updateLbVersionSeller(LbVersionSeller lbVersionSeller){
        sqlSession.update(NAMESPACE.concat("updateLbVersionSeller"), lbVersionSeller);
    }

    /**
     *
     * @param lbVersionSeller 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void deleteLbVersionSeller(LbVersionSeller lbVersionSeller){
        sqlSession.update(NAMESPACE.concat("deleteLbVersionSeller"), lbVersionSeller);
    }



    /**
     *
     * @param lbVersionSeller 수정할 상품 엔티티
     * @return 수정된 건 수
     */
    public void insertLbVersionSeller(LbVersionSeller lbVersionSeller){
        sqlSession.update(NAMESPACE.concat("insertLbVersionSeller"), lbVersionSeller);
    }


    /**
     * 버전별 셀러보회
     *
     * @param lbVersion, sellerId 조회할 버전
     * @return 셀러목록
     */
    public Integer selectLbVersionSellerByUk(String lbVersion, String sellerId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbVersion", lbVersion);
        paramMap.put("sellerId", Integer.parseInt(sellerId));
        return sqlSession.selectOne(NAMESPACE.concat("selectLbVersionSellerByUk"), paramMap);
    }


    /**
     * 버전별 셀러보회
     *
     * @param mapId, sellerId 조회할 버전
     * @return 셀러목록
     */
    public Integer selectLbVersionSellerById(Integer mapId){
        return sqlSession.selectOne(NAMESPACE.concat("selectLbVersionSellerById"), mapId);
    }


    /**
     * 버전별 셀러조회
     *
     * @param lbVersion, sellerId 조회할 버전
     * @return 셀러목록
     */
    public LbVersionSeller selectLbVersionSellerInfoByUk(String lbVersion, String sellerId){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lbVersion", lbVersion);
        paramMap.put("sellerId", Integer.parseInt(sellerId));
        return sqlSession.selectOne(NAMESPACE.concat("selectLbVersionSellerInfoByUk"), paramMap);
    }


    /**
     * 버전별 셀러조회
     *
     * @param lbVersion, sellerId 조회할 버전
     * @return 셀러목록
     */
    public Integer selectLbVersionSellerCount(String lbVersion){
        return sqlSession.selectOne(NAMESPACE.concat("selectLbVersionSellerCount"), lbVersion);
    }


    /**
     * 버전별 셀러조회
     *
     * @param id
     * @return 셀러매칭정보
     */
    public LbVersionSeller selectLbVersionSellerInfoById(Integer id){
        return sqlSession.selectOne(NAMESPACE.concat("selectLbVersionSellerInfoById"), id);
    }


}