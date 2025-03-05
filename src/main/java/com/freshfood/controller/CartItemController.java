package com.freshfood.controller;

import com.freshfood.dto.request.CartItemRequestDTO;
import com.freshfood.dto.response.ResponseData;
import com.freshfood.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/")
    public ResponseData<?> addCartItem(@RequestBody CartItemRequestDTO cartItemRequestDTO){
        return new ResponseData<>(HttpStatus.OK.value(), "Add cart item success", cartItemService.saveCartItem(cartItemRequestDTO));
    }
    @PutMapping("/{id}")
    public ResponseData updateCartItem(@PathVariable int id ,@RequestBody CartItemRequestDTO cartItem) {
        cartItemService.updateCartItem(id, cartItem);
        return new ResponseData(HttpStatus.OK.value(), "Updated success");
    }
    @DeleteMapping("/{id}")
    public ResponseData deleteCartItem(@PathVariable int id) {
        cartItemService.deleteCartItem(id);
        return new ResponseData(HttpStatus.OK.value(), "Delete cart item success");
    }

}
