defmodule PhoenixElm.ContactView do
  use PhoenixElm.Web, :view

  def render("index.json", %{page: page}), do: page
end
