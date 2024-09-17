package dev.jay.ultimatepokedex.api;

import java.util.List;

import dev.jay.ultimatepokedex.model.dto.response.SuccessDto;
import dev.jay.ultimatepokedex.model.dto.response.pokemon.Pokemon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PokemonApi {
    @GET("pokemon")
    Call<List<Pokemon>> getPokemons();

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemonById(@Path("id") int id);

    @PUT("pokemon/add")
    Call<SuccessDto> addPokemon(@Body Pokemon pokemon);

    @PATCH("pokemon/{id}")
    Call<SuccessDto> updatePokemonById(@Path("id") int id, @Body Pokemon pokemon);

    @DELETE("pokemon/{id}")
    Call<SuccessDto> deletePokemonById(@Path("id") int id);


}
