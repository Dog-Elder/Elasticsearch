package com.example.elasticsearch.mapper;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.elasticsearch.entity.Poetries;
import com.example.elasticsearch.entity.Product;
import com.example.elasticsearch.service.IPoetriesService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    //创建索引并增加映射配置
    @Test
    public void createIndex(){
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex(){
        boolean flg = elasticsearchRestTemplate.indexOps(Product.class).delete();
        System.out.println("删除索引 = " + flg);
    }

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomProductRepository repository;

    @Autowired
    private IPoetriesService poetriesService;
    @Autowired
    private PoetriesRepository poetriesRepository;

    /**
     * 新增
     */
    @Test
    public void save(){
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        productDao.save(product);
    }
    /**
     * 新增
     */
    @Test
    public void customization(){

        List<Product> res
                = repository.select(1, 5, "华为");

        System.out.println("res = " + res);
    }
    /**
     * 自定义查询 根据title查询
     */
    @Test
    public void findByTitle(){
        List<Product> byTitle = productDao.findAllByTitleOrTitle("小米","小米");
        byTitle.forEach(ele->{
            System.out.println("ele = " + ele);
        });
    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2

    //修改
    @Test
    public void update(){
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米 2 手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        product.setImages("http://www.atguigu/xm.jpg");
        productDao.save(product);
    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2


    //根据 id 查询
    @Test
    public void findById(){
        Product product = productDao.findById(2L).get();
        System.out.println(product);
    }

    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    //删除
    @Test
    public void delete(){
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2

    //批量新增
    @Test
    public void saveAll(){
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("["+i+"]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.atguigu/xm.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    /*@Autowired
    private ElasticsearchTemplate esTemplate;*/
    //分页查询
    @Test
    public void findByPageable(){
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int currentPage=0;//当前页，第一页从 0 开始， 1 表示第二页
        int pageSize = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize,sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product Product : productPage.getContent()) {
            System.out.println(Product);
        }

    }

    @Test
    public void myBatisTest() {
        List<Poetries> select = select(1, 20, "深秋");
        JSONArray objects = JSONUtil.parseArray(select);
        System.out.println(objects);
    }

    public List<Poetries> select(int page, int limit, String c) {
        //查询策略
        TermQueryBuilder builder = QueryBuilders.termQuery("content", c);
        HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*");
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightFields(highlightBuilder)
                .withPageable(PageRequest.of(page - 1, limit))
                .build();

        SearchHits<Poetries> search = elasticsearchRestTemplate.search(build, Poetries.class);
        List<SearchHit<Poetries>> searchHits = search.getSearchHits();
        List<Poetries> list = new ArrayList<>();
        for (SearchHit<Poetries> hit : searchHits) {
            Map<String, List<String>> map = hit.getHighlightFields();
            List<String> content = map.get("content");
            if (!CollectionUtils.isEmpty(content)) {
                hit.getContent().setContent(content.get(0));
            }
            list.add(hit.getContent());
        }
        return list;
    }
}