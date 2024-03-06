package com.thqu1et.e_commerces.service.Implementation;

import com.thqu1et.e_commerces.exception.ProductException;
import com.thqu1et.e_commerces.model.Category;
import com.thqu1et.e_commerces.model.Product;
import com.thqu1et.e_commerces.repository.CategoryRepository;
import com.thqu1et.e_commerces.repository.ProductRepository;
import com.thqu1et.e_commerces.request.CreateProductRequest;
import com.thqu1et.e_commerces.service.ProductService;
import com.thqu1et.e_commerces.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductSeviceImpl implements ProductService {

    final private ProductRepository productRepository;
    final private UserServiceImpl userServiceImp;
    final private CategoryRepository categoryRepository;

    public ProductSeviceImpl(ProductRepository productRepository, UserServiceImpl userServiceImpl,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userServiceImp = userServiceImpl;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {

        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());

        if (topLevel == null){
            Category topLavelCategory = new Category();
            topLavelCategory.setName(req.getTopLavelCategory());
            topLavelCategory.setLevel(1);

            topLevel =categoryRepository.save(topLavelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory() , topLevel.getName());

        if(secondLevel == null){
            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLavelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory() , secondLevel.getName());

        if(thirdLevel == null){
            Category thirdLavelCategory = new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory());
            thirdLavelCategory.setParentCategory(secondLevel);
            thirdLavelCategory.setLevel(2);

            thirdLevel = categoryRepository.save(thirdLavelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPersent());
        product.setCreatedAt(LocalDateTime.now());
        product.setBrand(req.getBrand());
        product.setCategory(thirdLevel);
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setSizes(req.getSize());
        product.setImageUrl(req.getImageUrl());

        Product savedProduct = productRepository.save(product);

        System.out.println("Products - " + product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);

        product.getSizes().clear();

        productRepository.delete(product);

        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {

        Product product = findProductById(productId);

        if (req.getQuantity() != 0 ){
            product.setQuantity(req.getQuantity());

        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {

        Optional<Product> opt = productRepository.findById(id);

        if (opt.isPresent()){
            return opt.get();
        }

        throw new ProductException("Product not found with ID" + id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes,
                                       Integer minPrice, Integer maxPrice, Integer minDiscount,
                                       String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber , pageSize);

        List<Product> products = productRepository.filterProducts(category,minPrice , maxPrice , minDiscount,sort);

        if (!colors.isEmpty()){
            products = products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if (stock != null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }else if(stock.equals("out_of_stock")){
                products = products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndext = Math.min(startIndex + pageable.getPageSize() , products.size());

        List<Product> pageContent = products.subList(startIndex,endIndext);

        Page<Product> filterProducts = new PageImpl<>(pageContent,pageable,products.size());

        return filterProducts;
    }
}
