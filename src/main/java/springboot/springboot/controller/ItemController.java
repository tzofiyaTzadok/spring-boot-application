package springboot.springboot.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.springboot.exception.ResourceNotFoundException;
import springboot.springboot.model.Item;
import springboot.springboot.repository.ItemRepository;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(value="Item Management System", description="Operations pertaining to item in Item Management System")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @ApiOperation(value = "View a list of available items", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @ApiOperation(value = "Get an item by item no")
    @GetMapping("/items/{itemNo}")
    public ResponseEntity<Item> getItemByItemNo(
            @ApiParam(value = "Item no from which item object will retrieve", required = true) @PathVariable(value = "itemNo") Long itemNo)
            throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this item no :: " + itemNo));
        return ResponseEntity.ok().body(item);
    }

    @ApiOperation(value = "Withdrawal quantity of an item from stock")
    @PutMapping("/items/withdraw/{itemNo}/{withdrawalQuantity}")
    public ResponseEntity<Item> WithdrawQuantity(
            @ApiParam(value = "Item no to Withdraw quantity of an item", required = true) @PathVariable(value = "itemNo") Long itemNo,
            @ApiParam(value = "Withdrawal quantity of an item", required = true) @PathVariable(value = "withdrawalQuantity") int withdrawalQuantity) throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this item no :: " + itemNo));
        item.setAmount(item.getAmount() - withdrawalQuantity);
        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @ApiOperation(value = "Deposit quantity of an item to stock")
    @PutMapping("/items/deposit/{itemNo}/{depositQuantity}")
    public ResponseEntity<Item> DepositQuantity(
            @ApiParam(value = "Item no to deposit quantity of an item", required = true) @PathVariable(value = "itemNo") Long itemNo,
            @ApiParam(value = "Deposit quantity of an item", required = true) @PathVariable(value = "depositQuantity") int depositQuantity) throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this item no :: " + itemNo));
        item.setAmount(item.getAmount() + depositQuantity);
        final Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @ApiOperation(value = "Add an item")
    @PostMapping("/items")
    public Item createItem(
            @ApiParam(value = "Item object store in database table", required = true) @Valid @RequestBody Item item) {
        return itemRepository.save(item);
    }

    @ApiOperation(value = "Delete an item")
    @DeleteMapping("/items/{itemNo}")
    public Map<String, Boolean> deleteItem(
            @ApiParam(value = "Item no from which item object will delete from database table", required = true) @PathVariable(value = "itemNo") Long itemNo)
            throws ResourceNotFoundException {
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found for this item no :: " + itemNo));
        itemRepository.delete(item);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
