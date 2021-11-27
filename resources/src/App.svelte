<script>
  export let name;
  export let viewsite;
  viewsite = false;
  let stockCount = 1;
  let singularPlural = "choice is";

  function toggle() {
    viewsite = !viewsite;
  }

  let promise = getRandomTicker();

  async function getRandomTicker() {
    const res = await fetch(`/random-stock/${stockCount}`, {method: 'GET'});
    const text = await res.text();


    if (stockCount == 1){
      singularPlural = "choice is";
    } else {
      singularPlural = "choices are";
    }

    
    if (res.ok) {
      return text;
    } else {
      throw new Error(text);
    }
    
  }

  function handleClick() {
    promise = getRandomTicker();
  }
  
</script>

<main>
	<h1>Random Stock Picker</h1>


{#if viewsite}
        
        <div style="width: 500px;margin: 0 auto">
          <h2>Don't know what to invest in?</h2>

          <h2>Have no edge over the pros?</h2>

          <h2>Just pick stocks at random!</h2>

          <h2>You'll probably do better than most hedge funds, and you'll pay a lot less than hedge fund clients do.</h2>

          <p>If you select enough stocks at random, then your overall performance will tend to match the performance of the market. This website will help you pick those random stocks.</p>

          <p>Enter the number of unique stocks you wish to pick. (Maximum is 100.) </p>

<input style="align: center" placeholder="1" bind:value={stockCount}>
          
<button on:click={handleClick}>
	Pick your stocks.
</button>

{#await promise}
	<p>...waiting</p>
{:then ticker}
	<p>Your {singularPlural}  <b>{ticker}</b>.</p>
{:catch error}
	<p style="color: red">{error.message}</p>
{/await}

        </div>

        {:else}

<div style="width: 500px;margin: 0 auto">
        
  <p>This site gives financial advice. To receive the advice, you must agree that you will hold yourself responsible for the consequences of following the advice. (You did it, it's your fault, not mine.)</p>


  <p> You must also agree that you will take no legal action against the author of the website or the hosting service that serves up the site. </p>

        <p>You must also understand that there is no guarantee that anything said on this website will be correct.</p>

        <p>You must also understand that the author of this website is not a financial advisor. Nor is the author your fiduciary. A financial advisor who is your fiduciary will be better qualified to give you financial advice. This is a website on the Internet written by some guy. Caveat emptor.</p>

        <p> THE WEBSITE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM, OUT OF OR IN CONNECTION WITH THE WEBSITE OR THE USE OR OTHER DEALINGS IN THE WEBSITE.</p>


	<button on:click={toggle}>
		I have read and understood everything above. I agree to the stated conditions.
	</button>

</div>
        
  {/if}
        
</main>

<style>
	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}

	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}

        p {
          text-align: left;
        }

        li {
          text-align: left;
        }
        
</style>
