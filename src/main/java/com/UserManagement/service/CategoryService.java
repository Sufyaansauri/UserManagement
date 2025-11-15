package com.UserManagement.service;

import com.UserManagement.dto.CategoryDTO;
import com.UserManagement.entity.Category;
import com.UserManagement.exceptions.Response;
import com.UserManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Response createCategory(CategoryDTO categoryDTO) {
        Response response = new Response();
        try {
            if (categoryDTO == null) {
                response.setCode("400");
                response.setMessage("Category object is null");
                return response;
            }

            if (categoryDTO.getTitle() == null || categoryDTO.getTitle().trim().isEmpty()) {
                response.setCode("400");
                response.setMessage("Category name cannot be empty");
                return response;
            }

            Category category = new Category();
            category.setTitle(categoryDTO.getTitle());
            Category savedCategory = categoryRepository.save(category);

            CategoryDTO setResponse = CategoryDTO.setResponse(savedCategory);


            response.setCode("201");
            response.setMessage("CATEGORY_CREATED_SUCCESSFULLY");
            response.setData("category", setResponse);

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error creating category: " + e.getMessage());
        }
        return response;
    }

    public Response updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Response response = new Response();
        try {
            Category existingCategory = categoryRepository.findById(categoryId).orElse(null);
            if (existingCategory == null) {
                response.setCode("404");
                response.setMessage("Category not found");
                return response;
            }

            if (categoryDTO.getTitle() != null && !categoryDTO.getTitle().trim().isEmpty()) {
                existingCategory.setTitle(categoryDTO.getTitle());
            }

            Category updatedCategory = categoryRepository.save(existingCategory);
            CategoryDTO setResponse = CategoryDTO.setResponse(updatedCategory);

            response.setCode("200");
            response.setMessage("CATEGORY_UPDATED_SUCCESSFULLY");
            response.setData("category", setResponse);

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error updating category: " + e.getMessage());
        }
        return response;
    }

    public Response deleteCategory(Long id){
        Response response = new Response();
        try {
          if(!categoryRepository.existsById(id) || id == null){
              response.setCode("400");
              response.setMessage("Product with id " + id + " not found");
              return response;
          }
          Category category = categoryRepository.findById(id);
          CategoryDTO setResponse = CategoryDTO.setResponse(category);

          response.setCode("200");
          response.setMessage("Category deleted successfully");
          response.setData("category", setResponse);

        }catch(Exception e) {
            e.printStackTrace();
            response.setMessage("Error retrieving categories: " + e.getMessage());
        }
        return response;
        }


    public Response getAllCategories(){
        Response response = new Response();
        try{
            List <Category> allCategories = categoryRepository.findAll();
            response.setCode("200");
            response.setMessage("categories retrieved successfully");
            response.setData("categories", allCategories);
        }catch(Exception e) {
            e.printStackTrace();
            response.setMessage("Error retrieving categories: " + e.getMessage());
        }
        return response;
        }

        public Response getCategoryById(Long categoryId) {
            Response response = new Response();
            try {
                if (!categoryRepository.existsById(categoryId) || categoryId == null) {
                    response.setCode("400");
                    response.setMessage("Category with id " + categoryId + " not found");
                    return response;
                }

                Category category = categoryRepository.findCategoryById(categoryId);
                CategoryDTO setResponse = CategoryDTO.setResponse(category);

                response.setCode("200");
                response.setMessage("Category retrieved successfully");
                response.setData("category", setResponse);
            } catch (Exception e) {
                e.printStackTrace();
                response.setMessage("Error retrieving category: " + e.getMessage());
            }
            return response;
        }

}
