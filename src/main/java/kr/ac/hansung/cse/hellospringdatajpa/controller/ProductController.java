package kr.ac.hansung.cse.hellospringdatajpa.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringdatajpa.dto.ProductCreateDTO;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"", "/"}) // products 또는 /products/ 둘 다 매핑
    public String viewHomePage(Model model) {

        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        return "index";
    }

    /*@GetMapping("/new")
    public String showNewProductPage(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);

        return "new_product";
    }*/
    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        model.addAttribute("product", new ProductCreateDTO()); // 이름은 "product" 유지
        return "new_product";
    }


    @GetMapping("/edit/{id}")
    public String showEditProductPage(@PathVariable(name = "id") Long id, Model model) {

        Product product = service.get(id);
        model.addAttribute("product", product);

        return "edit_product";
    }

    // @ModelAttribute는  Form data (예: name=Laptop&brand=Samsung&madeIn=Korea&price=1000.00)를 Product 객체
    // @RequestBody는 HTTP 요청 본문에 포함된
    //  JSON 데이터(예: {"name": "Laptop", "brand": "Samsung", "madeIn": "Korea", "price": 1000.00})를 Product 객체에 매핑
    /*@PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {

        service.save(product);

        return "redirect:/products";
    }*/
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") @Valid ProductCreateDTO productForm,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "new_product"; // 또는 edit_product: 상황에 맞게 조정
        }

        Product product = new Product(
                productForm.getName(),
                productForm.getBrand(),
                productForm.getMadeIn(),
                productForm.getPrice()
        );

        service.save(product);
        return "redirect:/products";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {

        service.delete(id);
        return "redirect:/products";
    }
}
