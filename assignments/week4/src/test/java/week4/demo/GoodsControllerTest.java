package week4.demo;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import week4.demo.Controller.GoodsController;
import week4.demo.Entity.Goods;
import week4.demo.Service.GoodsService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({GoodsController.class, GoodsService.class})
@AutoConfigureMockMvc
class GoodsControllerTest {
	Gson gson=new Gson();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GoodsService goodsService;

	@Test
	//验证HTTP请求状态正确性，返回格式和返回值
	public void testReturnCode() throws Exception {
		//查询空，返回值为json
		mockMvc.perform(MockMvcRequestBuilders.get("/goods/getGoods/{id}","1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").doesNotExist());
		//增
		Goods goods1=new Goods("1","柯基狗狗",1500,20,3);
		mockMvc.perform(MockMvcRequestBuilders.post("/goods/addGoods")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(goods1)))
				.andExpect(status().isOk());
		//查询具体内容，返回值为json
		mockMvc.perform(MockMvcRequestBuilders.get("/goods/getGoods/{id}","1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("柯基狗狗"));
		//增
		Goods goods2=new Goods("2","德牧狗狗",3000,10,3);
		mockMvc.perform(MockMvcRequestBuilders.post("/goods/addGoods")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(goods2)))
				.andExpect(status().isOk());
		//查询关键字，返回值为json
		mockMvc.perform(MockMvcRequestBuilders.get("/goods/findGoods/{name}","狗狗")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("柯基狗狗"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("德牧狗狗"));
		//删
		mockMvc.perform(MockMvcRequestBuilders.delete("/goods/deleteGoods/{id}","1"))
				.andExpect(status().isNoContent());
		//查询删除结果
		mockMvc.perform(MockMvcRequestBuilders.get("/goods/getGoods/{id}","1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$..name").doesNotExist());
		//改
		Goods goods3=new Goods("2","金毛狗狗",2500,30,4);
		mockMvc.perform(MockMvcRequestBuilders.put("/goods/updateGoods/{id}","2")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(goods3)))
				.andExpect(status().isOk());
		//查询修改结果，类型为json，同时测试每个字段的值
		mockMvc.perform(MockMvcRequestBuilders.get("/goods/getGoods/{id}","2")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("金毛狗狗"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(2500))
				.andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(30))
				.andExpect(MockMvcResultMatchers.jsonPath("$.saleroom").value(4));
	}

}
