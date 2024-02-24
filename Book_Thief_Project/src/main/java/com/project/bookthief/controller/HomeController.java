package com.project.bookthief.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookthief.dto.ProductDTO;
import com.project.bookthief.entity.Product;
import com.project.bookthief.global.GlobalData;
import com.project.bookthief.service.CategoryService;
import com.project.bookthief.service.ProductService;

@Controller
public class HomeController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	@GetMapping({ "/" })
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}

	@GetMapping({ "/home" })
	public String homeLogin(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index2";
	}

	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}

	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}

	@PostMapping("/payNow")
	public String paynow() {
		GlobalData.cart.clear();
		return "orderPlaced";
	}

	@GetMapping("/sell")
	public String getProdAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "productsAddUser";
	}

	@PostMapping("/sell")
	public String postSell(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName)
			throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if (!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		} else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);

		return "index2";
	}
}
