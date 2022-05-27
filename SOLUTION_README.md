## Solution

The chain-of-responsibility pattern is used to solve this challenge.

Each offer will be part of the chain of handlers, which are classes that extend the OfferHandler.class

The BillingGenerator.class will create the chain of handlers from an offer list and price list, and it
is responsible of generate the bill given a shopping cart. 

The process is as follows:

- The chain are set as follows: OfferHandler1 -> OfferHandler2 -> OfferHandler3 -> OfferHandler4

- Each of the offer handlers in the chain takes its turn at trying to handle the shopping cart it receives from the BillingGenerator.class

- If the offerHandler_i can handle it, then a BillDiscount object is created and added to the the list of bill discounts. Then if chain has a next offer handler, the shopping cart is sent to the handle offerHandler_i+1.

- If the offerHandler_i can not handle it, but chain has a next offer handler then the shopping cart is sent to the handle offerHandler_i+1.

### NOTE : 
As of now considering only 2 offers, i have created two separate methods "applyOffer" and "applyNextOffer" to handle the 2 offers. Further, we can re-factor and make the generic method to handle multiple offers based on the type of offer.

## For this Project I have Used :

- Java 8
- Maven
- GIT

## Handled Offers Scenarios :

- Buy A and get B for half price. Half price is applied to B items based on the number of A items.
- Buy any 3 items from a set of products {X, Y, Z, P, Q} and get the cheapest one for free.